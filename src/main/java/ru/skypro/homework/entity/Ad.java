package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name ="ads")
public class Ad extends ImageEntity {

    @JoinColumn(name="user_id")
    @ManyToOne
    private User author;

    private String title;

    private String description;

    private int price;

}
