package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO-container to return with the medhod AdController.get().
 * Contains information about the ad and about the owner of the ad.
 */
@Data
public class AdInfo {

    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private BigDecimal price;
    private String title;
}
