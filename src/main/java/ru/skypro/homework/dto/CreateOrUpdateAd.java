package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO-container to return with the medhod AdController.get().
 * Contains information about the ad and about the owner of the ad.
 */
@Data
public class CreateOrUpdateAd {

    @NotNull
    @Size(min = 4, max = 32)
    private String title;

    @Min(0)
    @Max(10_000_000)
    private int price;

    @NotNull
    @Size(min = 4, max = 64)
    private String description;

}
