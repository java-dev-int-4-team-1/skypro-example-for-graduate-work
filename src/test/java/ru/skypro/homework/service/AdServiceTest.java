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
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.BadImageException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.testutil.AdTestUtil;
import ru.skypro.homework.util.ImageManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    private CurrentUserService currentUserService;

    @MockBean
    private ImageManager imageManager;

    @ParameterizedTest
    @MethodSource("streamAdsDto")
    void getAll(Ads ads, List<Ad> adList) {
        //given
        User author = generateAuthor();
        //when
        when(adRepository.findAll()).thenReturn(adList);
        Ads adsDto =  adService.getAll();

        //then
        assertThat(adsDto).isNotNull();
        assertThat(adsDto.getCount()).isEqualTo(ads.getCount());
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

    /* ToDo when the target method is implemented */
    @Test
    void getAllByCurrentUser() {
    }

    @Test
    void getById() {
        //given
        Ad ad = generateAd();
        AdDto expected = adMapper.map(ad);
        int id = ad.getId();

        //when
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        ExtendedAd actual = adService.getById(id);

        //then
        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);

    }

    @Test
    void getById_whenNotFound() {
        //given
        Ad ad = generateAd();
        int id = ad.getId();

        //then
        assertThrows(
                AdNotFoundException.class,
                () -> adService.getById(id));

    }


    @Test
    void create() {
        //given
        CreateOrUpdateAd properties = generateCreateOrUpdateAd();
        MockMultipartFile image = new MockMultipartFile(IMAGE, IMAGE.getBytes());

        //when
        Ad ad = adMapper.map(properties, image);
        AdDto adDto = adMapper.map(ad);
        when(adRepository.save(ad)).thenReturn(ad);
        when(currentUserService.getCurrentUser()).thenReturn(generateAuthor());

        //then
        assertThat(adService.create(properties, image))
                .isNotNull()
                .isEqualTo(adDto);
    }

    @Test
    void delete() {
        //given
        Ad ad = generateAd();
        int id = ad.getId();
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));

        //when
        adService.delete(id);

        //then
        verify(adRepository).findById(id);
        verify(adRepository).delete(ad);
    }


    @Test
    void delete_whenNotFound() {
        //given
        Ad ad = generateAd();

        //then
        assertThrows(AdNotFoundException.class, () -> adService.delete(ad.getId()));
    }

    @Test
    void patchProperties() {
        //given
        User author = generateAuthor();
        Ad ad = generateAd(author, "Former Title", "Former Description", PRICE-1);
        CreateOrUpdateAd properties = generateCreateOrUpdateAd();
        Ad expected = generateAd(author);
        AdDto dtoExpected = adMapper.map(expected);
        final int id = ad.getId();

        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        when(adRepository.save(ad)).thenReturn(ad);

        //when
        AdDto result = adService.patchProperties(id, properties);

        //then
        verify(adRepository).findById(id);
        verify(adRepository).save(ad);
        assertThat(result)
                .isNotNull()
                .isEqualTo(dtoExpected);
    }


    @Test
    void patchProperties_whenNotFound() {
        //given
        CreateOrUpdateAd properties = generateCreateOrUpdateAd();
        int id = 0;

        //then
        assertThrows(AdNotFoundException.class,
                () -> adService.patchProperties(id, properties));
    }

    @Test
    void patchImage() {
        //given
        Ad ad = generateAd();
        ad.setImage("former " + IMAGE);
        int id = ad.getId();

        MockMultipartFile image = new MockMultipartFile(IMAGE, IMAGE.getBytes());
        Ad expected = generateAd();
        AdDto expectedDto = adMapper.map(expected);

        //when
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        when(adRepository.save(ad)).thenReturn(ad);
        AdDto result = adService.patchImage(id, image);

        //then
        verify(imageManager).uploadImage(ad, image);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDto);
    }

    @Test
    void patchImage_whenBadImageExceptionIsThrown() {
        //given
        Ad ad = generateAd();
        int id = ad.getId();
        MockMultipartFile image = new MockMultipartFile(IMAGE, IMAGE.getBytes());

        //when
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        when(imageManager.uploadImage(ad, image)).thenThrow(BadImageException.class);

        //then
        assertThrows(BadImageException.class,
                () -> adService.patchImage(id, image));
    }

    @Test
    void patchImage_whenNotFound() {
        //given
        MockMultipartFile image = new MockMultipartFile(IMAGE, IMAGE.getBytes());
        int id = 0;

        //then
        assertThrows(AdNotFoundException.class,
                () -> adService.patchImage(id, image));
    }
}