package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@CrossOrigin(value = "${cross-origin.url}")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @GetMapping("/{id}")
    public ExtendedAd get(@PathVariable Integer id) {
        log.trace("get(id={})", id);

        return adService.getById(id);
    }

    @GetMapping("/me")
    public Ads getMyAds() {
        log.trace("getMyAds");

        return adService.getAllByCurrentUser();
    }

    @GetMapping
    public Ads getAll() {
        log.trace("getAll");
        return adService.getAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PreAuthorize("@userService.isAuthenticated()")
     public AdDto create(
             @Valid @RequestPart CreateOrUpdateAd properties,
             @RequestPart MultipartFile image) {
        log.trace("create(properties={}, image.originalFilename={}))",
                properties,
                image.getOriginalFilename());

        return adService.create(properties, image);
    }

    @PatchMapping("/{id}")
    @Transactional
    @PreAuthorize("@userService.hasPermission(@adService.getAd(#id))")
    public AdDto patchProperties(
            @NotNull @PathVariable Integer id,
            @Valid @RequestBody CreateOrUpdateAd properties) {

        log.trace("patchProperties(id={}, properties={}", id, properties);

        return adService.patchProperties(id, properties);
    }

    @PatchMapping(path="/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("@userService.hasPermission(@adService.getAd(#id))")
    public AdDto patchImg(
            @NotNull @PathVariable Integer id,
            @RequestPart MultipartFile image) {

        log.trace("patchImage(id={}, image.originalFilename={}", id, image.getOriginalFilename());

        return adService.patchImage(id, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @PreAuthorize("@userService.hasPermission(@adService.getAd(#id))")
    public void delete(@NotNull @PathVariable Integer id) {

        log.trace("delete(id={}", id);

        adService.delete(id);
    }
}
