package com.blinker.atom.service;

import com.blinker.atom.dto.ContentInstanceRequestDto;
import com.blinker.atom.dto.ParsedSensorLogDto;
import com.blinker.atom.utils.EncodingUtil;
import com.blinker.atom.utils.HttpClientUtil;
import com.blinker.atom.utils.ParsingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ThingPlugService {

    @Value("${thingplug.base.url}")
    private String baseUrl;

    @Value("${thingplug.app.eui}")
    private String appEui;

    @Value("${thingplug.headers.x-m2m-origin}")
    private String origin;

    @Value("${thingplug.headers.uKey}")
    private String uKey;

    @Value("${thingplug.headers.x-m2m-ri}")
    private String requestId;

    public ParsedSensorLogDto getLatestContent(String remoteCseId) {
        // Step 1: Fetch Content Instance list
        String listUrl = String.format("%s/%s/v1_0/remoteCSE-%s/container-LoRa?fu=1&ty=4", baseUrl, appEui, remoteCseId);
        log.info("Fetching Content Instance list from URL: {}", listUrl);

        try {
            String listResponse = HttpClientUtil.get(listUrl, origin, uKey, requestId);
            log.debug("Content Instance List Response: {}", listResponse);

            // Step 2: Extract the most recent Content Instance URI
            String latestInstanceUri = extractLatestContentInstanceUri(listResponse);
            if (latestInstanceUri == null) {
                log.error("No content instances found for remoteCSE ID: {}", remoteCseId);
                throw new IllegalArgumentException("No content instances found.");
            }

            log.info("Latest Content Instance URI: {}", latestInstanceUri);

            // Step 3: Fetch the latest Content Instance data
            String instanceUrl = baseUrl + latestInstanceUri;
            log.info("Fetching latest Content Instance data from URL: {}", instanceUrl);

            String instanceResponse = HttpClientUtil.get(instanceUrl, origin, uKey, requestId);
            log.debug("Latest Content Instance Response: {}", instanceResponse);

            // Step 4: Extract and parse the <con> tag content
            String conData = extractContent(instanceResponse);
            if (conData == null) {
                log.error("No <con> data found in Content Instance: {}", latestInstanceUri);
                throw new IllegalArgumentException("No <con> data found.");
            }

            log.info("Extracted <con> data: {}", conData);
            return ParsingUtil.parseMessage(conData);

        } catch (HttpClientErrorException e) {
            log.error("HTTP Error during Content Instance processing. Status: {}, Error: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException("400 Bad Request: Check remoteCSE ID or request parameters.");
            }
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during Content Instance processing", e);
            throw e;
        }
    }

    private String extractLatestContentInstanceUri(String response) {
        // 정규 표현식을 사용해 Content Instance URI를 모두 추출
        Pattern pattern = Pattern.compile("(/" + appEui + "/v1_0/remoteCSE-[^/]+/container-LoRa/contentInstance-[^\\s]+)");
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            // 첫 번째 매칭된 URI 반환
            return matcher.group(1);
        } else {
            log.error("No valid Content Instance URI found in the response.");
            return null;
        }
    }

    private String extractContent(String response) {
        // Use regex to extract the <con> tag content
        Pattern pattern = Pattern.compile("<con>(.+?)</con>");
        Matcher matcher = pattern.matcher(response);
        return matcher.find() ? matcher.group(1) : null;
    }

    public String getAllContentInstances(String remoteCseId) {
        String url = String.format("%s/%s/v1_0/remoteCSE-%s/container-LoRa?fu=2&ty=4", baseUrl, appEui, remoteCseId);

        log.info("Fetching all content instances from URL: {}", url);
        log.debug("Request headers: X-M2M-Origin={}, uKey={}, ri={}", origin, uKey, requestId);

        try {
            String response = HttpClientUtil.get(url, origin, uKey, requestId);
            log.info("Response for all content instances: {}", response);
            return response;
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error during getAllContentInstances request. Status: {}, Error: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during getAllContentInstances request", e);
            throw e;
        }
    }

    public List<String> fetchRemoteCSEIds() {
        String url = String.format("%s/%s/v1_0?fu=1&ty=16", baseUrl, appEui);
        String response = HttpClientUtil.get(url, origin, uKey, requestId);

        return extractRemoteCSEIds(response);
    }

    private List<String> extractRemoteCSEIds(String response) {
        List<String> remoteCSEIds = new ArrayList<>();
        String pattern = "/remoteCSE-([a-zA-Z0-9]+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(response);

        while (matcher.find()) {
            remoteCSEIds.add(matcher.group(1)); // 매칭된 ID만 추가
        }

        return remoteCSEIds;
    }

    public String createContentInstance(ContentInstanceRequestDto request) {
        String url = String.format("%s/%s/v1_0/remoteCSE-%s/container-%s", baseUrl, appEui, request.getRemoteCseId(), request.getContainerName());

        // XML Body for contentInstance
        String body = String.format(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
            + "<cnf>application/json</cnf>"
            + "<con>%s</con>"
            + "</m2m:cin>", EncodingUtil.encodeToHex(request));

        log.info("Creating contentInstance at URL: {}", url);
        log.info("Creating contentInstance: {}", body);
        return HttpClientUtil.post(url, origin, uKey, requestId, body);
    }
}