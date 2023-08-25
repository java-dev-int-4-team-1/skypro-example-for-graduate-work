package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ImageEntity extends AbstractEntity {
    public static final String IMG_REALM = "/img/";

    /**
     * name-part of the link to the image
     */
    private String image;

    public String getImage() {
        return  IMG_REALM + this.getClass().getSimpleName().toLowerCase() + "/" + image;
    }

}
