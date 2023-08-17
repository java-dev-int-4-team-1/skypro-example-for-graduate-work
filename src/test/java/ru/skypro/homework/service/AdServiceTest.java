package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.testutil.AdTestUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdServiceTest extends AdTestUtil {

    @Autowired
    AdService adService;

    @ParameterizedTest
    @MethodSource("streamAdsDto")
    void getAll(Ads ads) {
        //when
        Ads adsDto =  adService.getAll();

        //then
        assertThat(adsDto).isNotNull();
        assertThat(adsDto.getCount()).isEqualTo(ads.getCount());
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


    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void patchProperties() {
    }

    @Test
    void patchImage() {
    }
}