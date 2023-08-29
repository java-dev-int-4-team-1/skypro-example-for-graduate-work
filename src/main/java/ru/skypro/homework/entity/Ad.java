package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="ads")
public class Ad extends ImageEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User author;

    private String title;

    private String description;

    private int price;

    @Override
    protected String getImageSubdirName() {
        return realmAds;
    }
}
