package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.Ads;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping("{id}")
    public ResponseEntity<?>get(@PathVariable Integer id) {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("me")
    public ResponseEntity<?>getMyAds() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<Ads>getAll() {

        return new ResponseEntity<>(new Ads(), HttpStatus.OK);
    }

    @PostMapping
     public ResponseEntity<?>create(@RequestPart CreateOrUpdateAd createAd, @RequestPart MultipartFile imgFile) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?>delete(@PathVariable Integer id) {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?>update(@PathVariable Integer id, @RequestPart CreateOrUpdateAd updateAd) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("{id}/image")
    public ResponseEntity<?>updateImg(@PathVariable Long id, @RequestBody MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
