package com.prokopchuk.picturesservice.controller;

import com.prokopchuk.picturesservice.exception.PicturesServiceException;
import com.prokopchuk.picturesservice.service.NasaPicturesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LargestPictureController {

    private final NasaPicturesService nasaPicturesService;

    @GetMapping("/mars/pictures/largest")
    @Cacheable("largestPictureCache")
    public ResponseEntity<byte[]> gerLargestPicture(@RequestParam(required = false) Integer sol,
                                      @RequestParam(required = false) String camera) {
        log.info("Process request '/mars/pictures/largest?sol={}&camera={}'", sol, camera);
        return nasaPicturesService.getLargestPicture(sol, camera);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(PicturesServiceException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

}
