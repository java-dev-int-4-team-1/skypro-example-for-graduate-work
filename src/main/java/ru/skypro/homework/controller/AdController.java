package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> get(@PathVariable Integer id) {
        return new ResponseEntity<>(adService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getMyAds() {
        return new ResponseEntity<>(adService.getAllByUser(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Ads> getAll() {
        return new ResponseEntity<>(adService.getAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<AdDto> create(
             @Valid @RequestPart CreateOrUpdateAd properties,
             @RequestPart MultipartFile image) {
        log.trace("create(properties={}, image={}))", properties, image);
        return new ResponseEntity<>(adService.create(properties, image), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@NotNull @PathVariable Integer id) {
        return new ResponseEntity<>(adService.delete(id)?
                HttpStatus.NO_CONTENT :
                HttpStatus.NOT_FOUND
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> patchProperties(
            @NotNull @PathVariable Integer id,
            @Valid @RequestBody CreateOrUpdateAd updateAd) {
        return new ResponseEntity<>(adService.patchProperties(id, updateAd), HttpStatus.OK);
    }

    @PatchMapping(path="/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto>patchImg(
            @NotNull @PathVariable Integer id,
            @RequestPart MultipartFile image) {
        return new ResponseEntity<>(adService.patchImage(id, image), HttpStatus.OK);
    }
}
