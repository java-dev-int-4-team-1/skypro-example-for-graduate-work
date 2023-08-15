package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdDto {

    /**
     * ad's id
     */
    private Long pk;
    /**
     * ad's author id
     */
    private int author;

    private String title;

    private int price;

    /**
     * link to the ad's image
     */
    private String image;


}
