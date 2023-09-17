package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import ru.skypro.homework.entity.ImageEntity;

abstract class ImageMapper {
    @Value("${realm.img}")
    protected String realmImg;

    @Value("${realm.users}")
    protected String realmUsers;
    @Value("${realm.ads}")
    protected String realmAds;

    protected String buildImageMapping(
            String entityRealm,
            ImageEntity entity) {
        return "/" + realmImg + "/" + entityRealm + "/" + entity.getImage();
    }

    public abstract String mapImage(ImageEntity entity);
}
