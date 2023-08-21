package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ImageEntity extends AbstractEntity {

    /**
     * name-part of the link to the image
     */
    private String image;

}
