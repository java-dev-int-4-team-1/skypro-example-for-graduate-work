package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class HomeworkEntity {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    /**
     * name-part of the link to the image
     */
    private String image;

}
