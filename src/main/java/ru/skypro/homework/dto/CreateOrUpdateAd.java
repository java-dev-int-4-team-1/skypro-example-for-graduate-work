package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.entity.Ad;

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
    private int pk;

    @NotNull
    @Size(min = 4, max = 32)
    private String title;

    @Min(0)
    @Max(10_000_000)
    private int price;

    @NotNull
    @Size(min = 4, max = 64)
    private String description;

    /** Used to modify existing ad entry which is retrieved from the db with the updated properties. **/
    public void updateAd(Ad ad) {

        ad.setDescription(description);
        ad.setPrice(price);
        ad.setTitle(title);

    }
}
