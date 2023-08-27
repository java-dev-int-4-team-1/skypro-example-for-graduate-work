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
public class AdService implements AdGetter {

    private final AdRepository adRepository;

    private final ImageManager imageManager;

    private final AdMapper adMapper;
    
    private final CurrentUserService currentUserService;
    
    /**
     * throws AdNotFoundException if there is no ad entry with the id in the db
     */
    public Ad getAd(Integer id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException(id)
                );
    }

    public Ads getAll() {
        log.debug("getAll");
        return adMapper.adsToDto(adRepository.findAll());
    }

    public Ads getAllByCurrentUser() {
        log.debug("getAllByCurrentUser");
        return adMapper.adsToDto(adRepository.findByAuthor(currentUserService.getCurrentUser()));
    }

    public AdDto getById(Integer id) {
        log.debug("getById({})", id);
        return adMapper.adToDto(getAd(id));
    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {

        log.debug("create({}, {})", properties, image);

        Ad ad = adMapper.createOrUpdateAdToAd(properties, image);
        ad.setAuthor(currentUserService.getCurrentUser());
        adRepository.save(ad);
        setImage(image, ad);

        return adMapper.adToDto(ad);
    }

    private void setImage(MultipartFile image, Ad ad) {
        ad.setImage(imageManager.uploadImg(ad, image));
        adRepository.save(ad);
    }

    public void delete(int id) {
        log.debug("delete({})", id);

        adRepository.delete(getAd(id));
    }

    public AdDto patchProperties(int id, CreateOrUpdateAd properties) {
        log.debug("patchProperties({}, {})", id, properties);

        Ad ad = getAd(id);
        properties.updateAd(ad);
        return adMapper.adToDto(adRepository.save(ad));
    }

    public AdDto patchImage(int id, MultipartFile image) {
        log.debug("patchImage({}, {})", id, image);

        Ad ad = getAd(id);
        setImage(image, ad);

        return adMapper.adToDto(adRepository.save(ad));
    }

}
