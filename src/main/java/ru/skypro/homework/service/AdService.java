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

@Slf4j
@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final ImageManager imageManager;

    private final AdMapper adMapper;
    
    private final CurrentUserService currentUserService;
    
    /**
     * throws AdNotFoundException if there is no ad entry with the pk in the db
     */
    private Ad getAd(Integer pk) {
        return adRepository.findById(pk)
                .orElseThrow(() -> new AdNotFoundException(pk)
                );
    }

    public Ads getAll() {
        log.debug("getAll");
        return adMapper.adsToAdsDto(adRepository.findAll());
    }

    /** ToDo: Not yet implemented */
    public Ads getAllByCurrentUser() {
        log.debug("getAllByCurrentUser");
        return adMapper.adsToAdsDto(adRepository.findByAuthor(currentUserService.getCurrentUser()));
    }

    public AdDto getById(Integer pk) {
        log.debug("getById({})", pk);
        return adMapper.adToAdDto(getAd(pk));
    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {

        log.debug("create({}, {})", properties, image);


        Ad ad = adRepository.save(
                adMapper.createOrUpdateAdToAd(properties, image)
        );

        uploadImg(ad, image);
        return adMapper.adToAdDto(ad);
    }

    private void uploadImg(Ad ad, MultipartFile image) {
        ad.setImage(imageManager.uploadImg(ad, image));
        adRepository.save(ad);
    }

    public void delete(int pk) {
        log.debug("delete({})", pk);

        adRepository.delete(getAd(pk));
    }

    public AdDto patchProperties(int pk, CreateOrUpdateAd properties) {
        log.debug("patchProperties({}, {})", pk, properties);

        Ad ad = getAd(pk);
        properties.updateAd(ad);
        return adMapper.adToAdDto(adRepository.save(ad));
    }

    public AdDto patchImage(int pk, MultipartFile image) {
        log.debug("patchImage({}, {})", pk, image);

        Ad ad = getAd(pk);
        ad.setImage(image.getName());
        uploadImg(ad, image);
        return adMapper.adToAdDto(adRepository.save(ad));
    }

}
