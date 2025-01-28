package com.blinker.atom.controller;

import com.blinker.atom.dto.ContentInstanceRequestDto;
import com.blinker.atom.dto.ParsedSensorLogDto;
import com.blinker.atom.service.ThingPlugService;
import com.blinker.atom.utils.ParsingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/skt")
public class ThingPlugController {

    private final ThingPlugService thingPlugService;

    @GetMapping("/{sensorId}/latest")
    public ParsedSensorLogDto getLatestContent(@PathVariable String sensorId) {
        try {
            // 1. 가장 최근 Content Instance 가져오기
            ParsedSensorLogDto parsedData = thingPlugService.getLatestContent(sensorId);

            /** 2. 모든 Content Instance 가져오기
            String allContentInstances = thingPlugService.getAllContentInstances(remoteCseId);
             **/
            return parsedData;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping("/remoteCSEs")
    public List<String> getRemoteCSEIds() {
        return thingPlugService.fetchRemoteCSEIds();
    }

    @PostMapping("/contentInstance")
    public String createContentInstance(
            @RequestBody ContentInstanceRequestDto content) {
        log.info("Received request to create contentInstance: remoteCseId={}, containerName={}, content={}",
                content.getRemoteCseId(), content.getContainerName(), content.getContent());
        return thingPlugService.createContentInstance(content);
    }
}
