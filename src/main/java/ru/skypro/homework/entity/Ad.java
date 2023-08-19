package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="ads")
public class Ad {
    /**
     * ad's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    @JoinColumn(name="user_id")
    @ManyToOne
    private User author;

    private String title;

    private String description;

    private int price;

    /**
     * link to the ad's image
     */
    private String image;

}
