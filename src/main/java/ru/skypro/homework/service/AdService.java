package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private static final  Ad emptyAd = new Ad();
    private static final User author = new User();

    static {
        emptyAd.setAuthor(author);
    }


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
        return adMapper.adToAdDto(new Ad());
    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {
        log.debug("create({}, {})", properties, image);
        return adMapper.adToAdDto(emptyAd);
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
        return adMapper.adToAdDto(emptyAd);
    }

    public AdDto patchImage(Integer id, MultipartFile image) {
        log.debug("patch({}, {})", id, image);
        return adMapper.adToAdDto(emptyAd);
    }

}
