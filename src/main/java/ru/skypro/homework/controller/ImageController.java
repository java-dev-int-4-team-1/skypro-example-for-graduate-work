package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "${cross-origin.value}")
@RequestMapping("/${realm.img}")
public class ImageController {

    @Value("${realm.users}")
    private String realmUsers;

    @Value("${realm.ads}")
    private String realmAds;
    private final ImageService imageService;

    @GetMapping("/${realm.users}/{filename}")
    public byte[] getUserImage(@PathVariable String filename) {

        log.trace("getUserImage(filename={})", filename);

        return imageService.getImage(realmUsers, filename);

    }

    @GetMapping("/${realm.ads}/{filename}")
    public byte[] getAdImage(@PathVariable String filename) {

        log.trace("getAdImage(filename={})", filename);

        return imageService.getImage(realmAds, filename);

    }
}
