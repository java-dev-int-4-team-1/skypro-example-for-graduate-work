package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.util.ImageManager;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final ImageManager imageManager;


    private final AdMapper adMapper;
    public Ads getAll() {
        log.debug("getAll");
        return adMapper.adsToAdsDto(adRepository.findAll());
    }

    public Ads getAllByUser() {
        log.debug("getAllByUser");
        return adMapper.adsToAdsDto(Collections.emptyList());
    }

    public AdDto getById(Integer id) {
        log.debug("getById({})", id);
        return adMapper.adToAdDto(
                adRepository.findById(id)
                        .orElseThrow(() -> new AdNotFoundException(id)
                        )
        );
    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {

        log.debug("create({}, {})", properties, image);

        Ad ad = adRepository.save(
                adMapper.createOrUpdateAdToAd(properties, image.getName())
        );
        try {
            imageManager.uploadAdImg(ad, image);
        } catch (IOException e)  {
            log.error("create({}, image.name={}): IOException was in ImageManager.uploadImage() thrown",
                    properties, image.getName(), e
            );
        }

        return adMapper.adToAdDto(ad);
    }

    /**
     *
     * @return true if the entry with the specified id was listed in the db, otherwise - false.
     */
    public boolean delete(Integer id) {
        log.debug("create({})", id);
        return false;
    }

    public AdDto patchProperties(Integer id, CreateOrUpdateAd updateAd) {
        log.debug("patch({}, {})", id, updateAd);
        return adMapper.adToAdDto(new Ad());
    }

    public AdDto patchImage(Integer id, MultipartFile image) {
        log.debug("patch({}, {})", id, image);
        return adMapper.adToAdDto(new Ad());
    }

}
