package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.testutil.AdTestUtil;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdMapperTest extends AdTestUtil {


    @Test
    void adToAdDto() {
        //given
        initAd();

        //when
        AdDto adDto = adMapper.adToAdDto(AD);

        //then
        assertThat(adDto).isNotNull();
        assertThat(adDto.getTitle()).isEqualTo(TITLE);
        assertThat(adDto.getImage()).isEqualTo(IMAGE);
        assertThat(adDto.getPrice()).isEqualTo(PRICE);
        assertThat(adDto.getAuthor())
                .isEqualTo(AUTHOR.getId());
    }

    @Test
    void adToExtendedAd() {
        //given
        initAd();

        //when
        ExtendedAd extendedAd = adMapper.adToExtendedAd(AD);

        //then
        assertThat(extendedAd).isNotNull();
        assertThat(extendedAd.getTitle()).isEqualTo(TITLE);
        assertThat(extendedAd.getImage()).isEqualTo(IMAGE);
        assertThat(extendedAd.getPrice()).isEqualTo(PRICE);
        assertThat(extendedAd.getAuthorFirstName())
                .isEqualTo(AUTHOR.getFirstName());
        assertThat(extendedAd.getAuthorLastName())
                .isEqualTo(AUTHOR.getLastName());
        assertThat(extendedAd.getEmail())
                .isEqualTo(AUTHOR.getEmail());
        assertThat(extendedAd.getPhone())
                .isEqualTo(AUTHOR.getPhone());
    }

    @Test
    void createOrUpdateAdToAd() {
        //given
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setDescription(DESCRIPTION);
        createOrUpdateAd.setPrice(PRICE);
        createOrUpdateAd.setTitle(TITLE);

        //when
        Ad ad =  adMapper.createOrUpdateAdToAd(createOrUpdateAd, IMAGE);

        //then
        assertThat(ad).isNotNull();
        assertThat(ad.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(ad.getPrice()).isEqualTo(PRICE);
        assertThat(ad.getTitle()).isEqualTo(TITLE);
    }


    @ParameterizedTest()
    @MethodSource("streamAds")
    void adsToAdsDto(Collection<Ad> ads) {
        //when
        Ads adsDto =  adMapper.adsToAdsDto(ads);

        //then
        assertThat(adsDto).isNotNull();
        assertThat(adsDto.getCount()).isEqualTo(ads.size());
        adsDto.getResults()
                        .forEach(adDto -> {
                            assertThat(adDto).isNotNull();
                            assertThat(adDto.getTitle()).startsWith(TITLE);
                            assertThat(adDto.getImage()).isEqualTo(IMAGE);
                            assertThat(adDto.getPrice()).isEqualTo(PRICE);
                            assertThat(adDto.getAuthor())
                                    .isEqualTo(AUTHOR.getId());
                        });
    }
}

