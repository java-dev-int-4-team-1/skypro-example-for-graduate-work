package ru.skypro.homework.testutil;

import org.junit.jupiter.params.provider.Arguments;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@MappedSuperclass
@Component
public class AdTestUtil {

    @Autowired
    protected AdMapper adMapper;
    protected final static int PK = 111;
    protected final static String TITLE = "title";
    protected final static String DESCRIPTION = "description";
    protected static String IMAGE = "image";
    protected final static int PRICE = 1_000;

    protected final static User AUTHOR = new User();

    protected static final Ad AD = new Ad();

    public static void fillTheAd(Ad ad) {
        fillTheAd(ad, AUTHOR);
    }

    public static void fillTheAd(Ad ad, User author) {
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


    public static void initAd() {
        fillTheAd(AD);
    }

    public static Ad createAdWithTheTitle(String title) {
        Ad ad = new Ad();
        fillTheAd(ad);
        ad.setTitle(title);
        return ad;
    }

    //given
    public static Stream<List<Ad>> streamAds() {
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

    public static Stream<Arguments> streamAdsDto() {

       // return Stream.of(Arguments.of(new Ads(), Collections.EMPTY_LIST));

        return streamAds()
                .map(ads-> Arguments.of(
                        Mappers.getMapper(AdMapper.class).adsToAdsDto(ads),
                        ads)
                );
    }
}
