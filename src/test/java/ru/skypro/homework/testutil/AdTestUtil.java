package ru.skypro.homework.testutil;

import org.junit.jupiter.params.provider.Arguments;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AdTestUtil {
    protected final static int PK = 111;
    protected final static String TITLE = "title";
    protected final static String DESCRIPTION = "description";
    protected static String IMAGE = "image";
    protected final static int PRICE = 1_000;

    protected final static User AUTHOR = new User();

    protected static final Ad AD = new Ad();

    protected static void fillTheAd(Ad ad) {
        fillTheAd(ad, AUTHOR);
    }

    protected static void fillTheAd(Ad ad, User author) {
        ad.setPk(PK);
        ad.setTitle(TITLE);
        ad.setDescription(DESCRIPTION);
        ad.setAuthor(author);
        ad.setImage(IMAGE);
        ad.setPrice(PRICE);

        author.setFirstName("John");
        author.setLastName("Smith");
        author.setEmail("e@mail.org");
        author.setPhone("88001002030");
    }


    protected static void initAd() {
        fillTheAd(AD);
    }

    protected static Ad generateAdWithTheTitle(String title) {
        Ad ad = new Ad();
        fillTheAd(ad);
        ad.setTitle(title);
        return ad;
    }

    protected static CreateOrUpdateAd generateCreateOrUpdateAd() {
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setPk(PK);
        createOrUpdateAd.setDescription(DESCRIPTION);
        createOrUpdateAd.setPrice(PRICE);
        createOrUpdateAd.setTitle(TITLE);
        return createOrUpdateAd;
    }

    //given
    public static Stream<List<Ad>> streamAds() {
        return Stream.of(
                new ArrayList<>(0),
                List.of(generateAdWithTheTitle(TITLE + 0)),
                List.of(
                        generateAdWithTheTitle(TITLE + 0),
                        generateAdWithTheTitle(TITLE + 1),
                        generateAdWithTheTitle(TITLE + 2)
                )
        );
    }

    public static Stream<Arguments> streamAdsDto() {

       // return Stream.of(Arguments.of(new Ads(), Collections.EMPTY_LIST));

        return streamAds()
                .map(ads-> Arguments.of(
                        Mappers.getMapper(AdMapper.class).adsToAdsDto(ads),
                        ads)
                );
    }
}
