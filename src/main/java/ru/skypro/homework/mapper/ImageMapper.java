package ru.skypro.homework.mapper;

import ru.skypro.homework.entity.ImageEntity;

abstract class ImageMapper {
    protected String buildImageMapping(
            String imgRealm,
            String entityRealm,
            ImageEntity entity) {
        return "/" + imgRealm + "/" + entityRealm + "/" + entity.getImage();
    }

    public abstract String mapImage(ImageEntity entity);
}
