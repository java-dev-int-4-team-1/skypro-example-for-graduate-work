package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.config.AdAppConstants;
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
public class CreateOrUpdateAd implements AdAppConstants {
    private int pk;

    @NotNull
    @Size(min = AD_TITLE_LENGTH_MIN, max = AD_TITLE_LENGTH_MAX)
    private String title;

    @Min(AD_PRICE_MIN)
    @Max(AD_PRICE_MAX)
    private int price;

    @NotNull
    @Size(min = AD_DESCRIPTION_LENGTH_MIN, max = AD_DESCRIPTION_LENGTH_MAX)
    private String description;

    /** Used to modify existing ad entry which is retrieved from the db with the updated properties. **/
    public void updateAd(Ad ad) {

        ad.setDescription(description);
        ad.setPrice(price);
        ad.setTitle(title);

    }
}
