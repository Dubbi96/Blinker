package com.blinker.atom.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpClientUtil {

    public static String get(String url, String origin, String uKey, String requestId) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/xml");
        headers.set("X-M2M-Origin", origin);
        headers.set("X-M2M-RI", requestId);
        headers.set("uKey", uKey);
        log.info("headers: {}", headers);
        log.info("url: {}", url);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public static String post(String url, String origin, String uKey, String requestId, String body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/xml");
        headers.set("Content-Type", "application/vnd.onem2m-res+xml;ty=4");
        headers.set("X-M2M-Origin", origin);
        headers.set("X-M2M-RI", requestId);
        headers.set("uKey", uKey);

        log.info("headers: {}", headers);
        log.info("url: {}", url);
        log.info("body: {}", body);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("Response Status: {}", response.getStatusCode());
        log.info("Response Body: {}", response.getBody());

        return response.getBody();
    }
}