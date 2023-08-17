package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdMapperTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static int PRICE = 1_000;

    private final static User AUTHOR = new User();

    @Autowired
    private AdMapper adMapper;

    private static final Ad ad = new Ad();

    public static void fillTheAd(Ad ad) {
        ad.setTitle(TITLE);
        ad.setDescription(DESCRIPTION);
        ad.setAuthor(AUTHOR);
        ad.setImage(IMAGE);
        ad.setPrice(PRICE);

        AUTHOR.setFirstName("John");
        AUTHOR.setLastName("Smith");
        AUTHOR.setEmail("e@mail.org");
        AUTHOR.setPhone("88001002030");
    }

    //given
    public static void initAd() {
        fillTheAd(ad);
    }

    public static Ad createAdWithTheTitle(String title) {
        Ad ad = new Ad();
        fillTheAd(ad);
        ad.setTitle(title);
        return ad;
    }


    @Test
    void adToAdDto() {
        //given
        initAd();

        //when
        AdDto adDto = adMapper.adToAdDto(ad);

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
        ExtendedAd extendedAd = adMapper.adToExtendedAd(ad);

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
        Ad ad =  adMapper.createOrUpdateAdToAd(createOrUpdateAd);

        //then
        assertThat(ad).isNotNull();
        assertThat(ad.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(ad.getPrice()).isEqualTo(PRICE);
        assertThat(ad.getTitle()).isEqualTo(TITLE);
    }

    //given
    public static Stream<Collection<Ad>> streamAds() {
        return Stream.of(
                new ArrayList<>(0),
                List.of(createAdWithTheTitle(TITLE + 0)),
                List.of(
                        createAdWithTheTitle(TITLE + 0),
                        createAdWithTheTitle(TITLE + 1),
                        createAdWithTheTitle(TITLE + 2)
                )
        );
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

