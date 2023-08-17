package ru.skypro.homework.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@MappedSuperclass
@Component
public class AdTestUtil {

    @Autowired
    protected AdMapper adMapper;
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

    public static Stream<Ads> streamAdsDto() {

        return Stream.of(new Ads());
        /*return streamAds()
                .map(ads->Mappers.getMapper(AdMapper.class).adsToAdsDto(ads));*/
    }
}
