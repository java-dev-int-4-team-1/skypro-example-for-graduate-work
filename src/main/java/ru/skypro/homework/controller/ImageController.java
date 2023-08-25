package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "${cross-origin.value}")
@RequestMapping("/img")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/user/{filename}")
    public byte[] getUserImage(@PathVariable String filename) {
        return imageService.getUserImage(filename);
    }

    @GetMapping("/ad/{filename}")
    public byte[] getAdImage(@PathVariable String filename) {
        return imageService.getAdImage(filename);
    }
}
