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

    /**
     *  @return the name of the subdirectory, which
     * is specified to store the images
     * of  entity's instances
     */
    public String getEntityImageSubdirName() {
        return this.getClass().getSimpleName().toLowerCase();

    }

    /**
     *  @return the name of the directory, which
     * is specified to store the images
     * of  entity's instances
     */
    public String getEntityImageDirName() {
        return IMG_REALM + getEntityImageSubdirName();

    }

    /**
     *  @return the path to the image consisting of
     *  the directory path and the filename.
     */
    public String getImageFullPath() {
        return  getEntityImageDirName()+ "/" + image;
    }

}
