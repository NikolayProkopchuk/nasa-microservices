package com.prokopchuk.picturesservice.service;

import java.net.URI;
import java.util.Map;

import com.prokopchuk.picturesservice.client.NasaProxyClient;
import com.prokopchuk.picturesservice.exception.PicturesServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NasaPicturesService {

    private final RestTemplate restTemplate;
    private final NasaProxyClient nasaProxyClient;

    public ResponseEntity<byte[]> getLargestPicture(Integer sol, String camera) {
        return nasaProxyClient.getPictures(sol, camera)
          .photos()
          .parallelStream()
          .map(photo -> getActualImageUrlSizeEntry(photo.imgSrc()))
          .max(Map.Entry.comparingByValue())
          .map(entry -> restTemplate.getForEntity(URI.create(entry.getKey()), byte[].class))
          .orElseThrow(() -> new PicturesServiceException(
            String.format("No images found with params sol: %d, camera: %s", sol, camera)));
    }

    private Map.Entry<String, Long> getActualImageUrlSizeEntry(String url) {
        var resp = restTemplate.exchange(url, HttpMethod.HEAD, null, Void.class);
        if (resp.getStatusCode().is2xxSuccessful()) {
            return Map.entry(url, resp.getHeaders().getContentLength());
        }
        if (resp.getStatusCode().is3xxRedirection()) {
            return getActualImageUrlSizeEntry(String.valueOf(resp.getHeaders().getLocation()));
        }

        throw new PicturesServiceException("Wrong image url: " + url);
    }
}
