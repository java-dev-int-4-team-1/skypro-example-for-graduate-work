package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.Ads;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping("/{id}")
    public ResponseEntity<?>get(@PathVariable Integer id) {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/me")
    public ResponseEntity<?>getMyAds() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<Ads>getAll() {

        return new ResponseEntity<>(new Ads(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<?>create(
             @Valid @RequestPart CreateOrUpdateAd properties,
             @RequestPart MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable Integer id) {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable Integer id, @Valid @RequestBody CreateOrUpdateAd updateAd) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping(path="/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?>updateImg(@PathVariable Long id, @RequestPart MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
