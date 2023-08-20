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
     * unique part of link to the image
     */
    private String image;

}
