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

    /**
     * throws AdNotFoundException if there is no ad entry with the id in the db 
     */
    private Ad getAd(Integer id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException(id)
                );
    }

    private void uploadImg(Ad ad, MultipartFile image) {
        try {
            imageManager.uploadAdImg(ad, image);
        } catch (IOException e)  {
            log.error("uploadImg({}, image.name={}): IOException was thrown",
                    ad, image.getName(), e
            );
        }
    }

    public AdDto getById(Integer id) {
        log.debug("getById({})", id);
        return adMapper.adToAdDto(getAd(id));
    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {

        log.debug("create({}, {})", properties, image);

        Ad ad = adRepository.save(
                adMapper.createOrUpdateAdToAd(properties, image.getName())
        );
        uploadImg(ad, image);

        return adMapper.adToAdDto(ad);
    }

    public void delete(int id) {
        log.debug("delete({})", id);

        adRepository.delete(getAd(id));
    }

    public AdDto patchProperties(int id, CreateOrUpdateAd updateAd) {
        log.debug("patchProperties({}, {})", id, updateAd);

        Ad ad = getAd(id);
        updateAd.updateAd(ad);
        adRepository.save(ad);        
        return adMapper.adToAdDto(new Ad());
    }

    public AdDto patchImage(int id, MultipartFile image) {
        log.debug("patchImage({}, {})", id, image);
        Ad ad = getAd(id);
        ad.setImage(image.getName());
        uploadImg(ad, image);
        adRepository.save(ad);
        return adMapper.adToAdDto(new Ad());
    }

}
