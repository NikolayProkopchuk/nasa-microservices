package com.prokopchuk.proxy.controller;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProxyController {

    private final RestTemplate restTemplate;

    @Value("${nasa.url}")
    private String nasaUrl;

    @Value("${nasa.apiKey}")
    private String apiKey;

    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos")
    public ResponseEntity<JsonNode> proxy(@RequestParam(required = false) Integer sol,
                                          @RequestParam(required = false) String camera) {
        var url = UriComponentsBuilder.fromUriString(nasaUrl)
          .queryParam("api_key", apiKey)
          .queryParamIfPresent("sol", Optional.ofNullable(sol))
          .queryParamIfPresent("camera", Optional.ofNullable(camera))
          .build()
          .toUri();
        return restTemplate.getForEntity(url, JsonNode.class);
    }
}
