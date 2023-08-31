package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.testutil.AdTestUtil;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class AdMapperTest extends AdTestUtil {

    @Autowired
    private AdMapper adMapper;

    @Test
    void adToAdDto() {
        //given
        User author = new User();
        Ad ad = generateAd(author);

        //when
        AdDto adDto = adMapper.map(ad);

        //then
        assertThat(adDto).isNotNull();
        assertThat(adDto.getTitle()).isEqualTo(TITLE);
        assertThat(adDto.getImage()).isEqualTo(IMAGE);
        assertThat(adDto.getPrice()).isEqualTo(PRICE);
        assertThat(adDto.getAuthor())
                .isEqualTo(author.getId());
    }

    @Test
    void adToExtendedAd() {
        //given
        User author = new User();
        Ad ad = generateAd(author);

        //when
        ExtendedAd extendedAd = adMapper.mapToExtendedAd(ad);

        //then
        assertThat(extendedAd).isNotNull();
        assertThat(extendedAd.getPk()).isEqualTo(ID);
        assertThat(extendedAd.getTitle()).isEqualTo(TITLE);
        assertThat(extendedAd.getImage()).isEqualTo(IMAGE);
        assertThat(extendedAd.getPrice()).isEqualTo(PRICE);
        assertThat(extendedAd.getAuthorFirstName())
                .isEqualTo(author.getFirstName());
        assertThat(extendedAd.getAuthorLastName())
                .isEqualTo(author.getLastName());
        assertThat(extendedAd.getEmail())
                .isEqualTo(author.getEmail());
        assertThat(extendedAd.getPhone())
                .isEqualTo(author.getPhone());
    }

    @Test
    void createOrUpdateAdToAd() {
        //given
        CreateOrUpdateAd createOrUpdateAd = generateCreateOrUpdateAd();

        //when
        Ad ad =  adMapper.map(
                createOrUpdateAd,
                new MockMultipartFile(IMAGE, IMAGE.getBytes()));

        //then
        assertThat(ad).isNotNull();
        assertThat(ad.getId()).isEqualTo(ID);
        assertThat(ad.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(ad.getPrice()).isEqualTo(PRICE);
        assertThat(ad.getTitle()).isEqualTo(TITLE);
    }




    @ParameterizedTest()
    @MethodSource("streamAds")
    void adsToAdsDto(Collection<Ad> ads) {
        //when
        User author = new User();
        Ads adsDto =  adMapper.map(ads);


        //then
        assertThat(adsDto).isNotNull();
        assertThat(adsDto.getCount()).isEqualTo(ads.size());
        adsDto.getResults()
                        .forEach(adDto -> {
                            assertThat(adDto).isNotNull();
                            assertThat(adDto.getPk()).isEqualTo(ID);
                            assertThat(adDto.getTitle()).startsWith(TITLE);
                            assertThat(adDto.getImage()).isEqualTo(IMAGE);
                            assertThat(adDto.getPrice()).isEqualTo(PRICE);
                            assertThat(adDto.getAuthor())
                                    .isEqualTo(author.getId());
                        });
    }
}

