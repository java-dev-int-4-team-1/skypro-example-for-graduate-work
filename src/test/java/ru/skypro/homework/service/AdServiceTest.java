package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.testutil.AdTestUtil;
import ru.skypro.homework.util.ImageManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AdServiceTest extends AdTestUtil {

    @Autowired
    private AdService adService;

    @Autowired
    private AdMapper adMapper;

    @MockBean
    private AdRepository adRepository;

    @MockBean
    private ImageManager imageMapper;

    @ParameterizedTest
    @MethodSource("streamAdsDto")
    void getAll(Ads ads, List<Ad> adList) {
        //given
        User author = new User();
        //when
        when(adRepository.findAll()).thenReturn(adList);
        Ads adsDto =  adService.getAll();

        //then
        assertThat(adsDto).isNotNull();
        assertThat(adsDto.getCount()).isEqualTo(ads.getCount());
        adsDto.getResults()
                .forEach(adDto -> {
                    assertThat(adDto).isNotNull();
                    assertThat(adDto.getPk()).isEqualTo(PK);
                    assertThat(adDto.getTitle()).startsWith(TITLE);
                    assertThat(adDto.getImage()).isEqualTo(IMAGE);
                    assertThat(adDto.getPrice()).isEqualTo(PRICE);
                    assertThat(adDto.getAuthor())
                            .isEqualTo(author.getId());
                });
    }


    @Test
    void create() {
        //given
        CreateOrUpdateAd properties = generateCreateOrUpdateAd();
        MockMultipartFile image = new MockMultipartFile(IMAGE, IMAGE.getBytes());

        //when
        Ad ad = adMapper.createOrUpdateAdToAd(properties, image);
        AdDto adDto = adMapper.adToAdDto(ad);
        when(adRepository.save(ad)).thenReturn(ad);

        //then
        assertThat(adService.create(properties, image))
                .isNotNull()
                .isEqualTo(adDto);
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