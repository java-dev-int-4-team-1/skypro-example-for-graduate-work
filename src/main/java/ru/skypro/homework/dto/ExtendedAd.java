package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO-container to return with the medhod AdController.get().
 * Contains information about the ad and about the owner of the ad.
 */
@Data
public class ExtendedAd {
    /** ad's id */
    private int pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    /** Author's login */
    private String email;
    /** short link to the ad's image */
    private String image;
    private String phone;
    private int price;
    private String title;
}
