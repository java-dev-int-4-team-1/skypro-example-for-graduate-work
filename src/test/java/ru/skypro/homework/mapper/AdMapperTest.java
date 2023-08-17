package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

class AdMapperTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static int PRICE = 1_000;

    private final User AUTHOR = new User();



    @Test
    void adToAdDto() {
        //given
        Ad ad = new Ad();
        ad.setTitle(TITLE);
        ad.setDescription(DESCRIPTION);
        ad.setAuthor(AUTHOR);
        ad.setImage(IMAGE);
        ad.setPrice(PRICE);

        //when
        AdDto adDto = AdMapper.INSTANCE.adToAdDto(ad);

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
    }

    @Test
    void createOrUpdateAdToAd() {
    }
}

