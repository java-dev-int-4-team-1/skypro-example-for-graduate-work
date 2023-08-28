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
@RequestMapping("/img")
public class ImageController {

    @Value("${img.subdir.users}")
    private String USERS_SUBDIR;

    @Value("${img.subdir.ads}")
    private String ADS_SUBDIR;
    private final ImageService imageService;

    @GetMapping("/${img.subdir.users}/{filename}")
    public byte[] getUserImage(@PathVariable String filename) {

        return imageService.getImage(USERS_SUBDIR, filename);
    }

    @GetMapping("/${img.subdir.ads}/{filename}")
    public byte[] getAdImage(@PathVariable String filename) {

        return imageService.getImage(ADS_SUBDIR, filename);
    }
}
