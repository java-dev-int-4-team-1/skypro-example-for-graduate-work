package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.EditForbiddenException;
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
        log.trace("getAll");
        return adMapper.map(adRepository.findAll());
    }

    public Ads getAllByCurrentUser() {
        User currentUser = currentUserService.getCurrentUser();
        log.trace("getAllByCurrentUser: user.id={}", currentUser.getId());
        return adMapper.map(adRepository.findByAuthor(currentUser));
    }

    public AdDto getById(Integer id) {
        log.trace("getById({})", id);
        return adMapper.map(getAd(id));
    }

    private void verifyEditPermission(Ad ad) {

        User currentUser = currentUserService.getCurrentUser();
        int currentUserId = currentUser.getId();
        int authorId = ad.getAuthor().getId();

        log.trace("verifyEditPermission(ad.author.id={}, current-user.id={})",
                authorId,
                currentUserId);

        if(currentUser.getRole() == Role.ADMIN) {
            return;
        }

        if(currentUserId != authorId) {
            throw new EditForbiddenException(currentUser, authorId);
        }

    }

    public AdDto create(CreateOrUpdateAd properties, MultipartFile image) {

        log.trace("create(properties={}, image.filename={})", properties, image.getOriginalFilename());

        Ad ad = adMapper.map(properties, image);
        ad.setAuthor(currentUserService.getCurrentUser());
        ad = adRepository.save(ad);
        setImage(image, ad);

        return adMapper.map(ad);
    }

    private void setImage(MultipartFile image, Ad ad) {
        log.trace("setImage(image.filename={}, ad.id={})",
                image.getOriginalFilename(),
                ad.getId());

        ad.setImage(imageManager.uploadImage(ad, image));
        adRepository.save(ad);
    }

    public void delete(int id) {
        log.trace("delete({})", id);
        Ad ad = getAd(id);
        verifyEditPermission(ad);
        adRepository.delete(getAd(id));
    }

    public AdDto patchProperties(int id, CreateOrUpdateAd properties) {
        log.trace("patchProperties({}, {})", id, properties);

        Ad ad = getAd(id);
        verifyEditPermission(ad);

        properties.updateAd(ad);
        return adMapper.map(adRepository.save(ad));
    }

    public AdDto patchImage(int id, MultipartFile image) {
        log.trace("patchImage({}, {})", id, image);

        Ad ad = getAd(id);
        verifyEditPermission(ad);

        setImage(image, ad);

        return adMapper.map(adRepository.save(ad));
    }

}
