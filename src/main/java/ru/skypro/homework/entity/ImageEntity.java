package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ImageEntity extends AbstractEntity {
    public enum Realm {
        USER, AD
    }

    /**
     * name-part of the link to the image
     */
    private String image;

    /**
     *  @return the name of the subdirectory, which
     * is specified to store the images
     * of the specific entity's instances
     */
    public abstract Realm getRealm() ;

}
