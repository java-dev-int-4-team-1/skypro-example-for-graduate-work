package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "${crossorigin.url}")
@RequestMapping("/${realm.img}")
public class ImageController {

    @Value("${realm.users}")
    private String realmUsers;

    @Value("${realm.ads}")
    private String realmAds;
    private final ImageService imageService;

    @GetMapping("/${realm.users}/{filename}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String filename) {

        log.trace("getUserImage(filename={})", filename);
        return getImage(realmUsers, filename);
    }

    @GetMapping("/${realm.ads}/{filename}")
    public ResponseEntity<byte[]>  getAdImage(@PathVariable String filename) {

        log.trace("getAdImage(filename={})", filename);
        return getImage(realmAds, filename);

    }

    private ResponseEntity<byte[]> getImage(String realm, String filename) {

        log.trace("getImage(realm={}, filename={})", realm, filename);
        return  ResponseEntity
                .ok()
                .cacheControl(
                        CacheControl.maxAge(
                                Duration.of(
                                        1, ChronoUnit.HOURS)))
                .body(imageService.getImage(realm, filename));
    }

}
