package com.prokopchuk.picturesservice.client;

import com.prokopchuk.picturesservice.dto.Photos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nasaClient", url = "${proxy.url}")
public interface NasaProxyClient {

    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos")
    Photos getPictures(@RequestParam(required = false) Integer sol,
                       @RequestParam(required = false) String camera);
}
