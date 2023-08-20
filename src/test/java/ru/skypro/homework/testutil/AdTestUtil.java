package ru.skypro.homework.testutil;

import org.junit.jupiter.params.provider.Arguments;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@MappedSuperclass
public class AdTestUtil {
    protected final static int PK = 111;
    protected final static String TITLE = "title";
    protected final static String DESCRIPTION = "description";
    protected static String IMAGE = "image";
    protected final static int PRICE = 1_000;

    protected static User generateAuthor() {
        User author = new User();
        author.setFirstName("John");
        author.setLastName("Smith");
        author.setEmail("e@mail.org");
        author.setPhone("88001002030");

        return author;
    }
    protected static Ad generateAd(User author) {
        return generateAd(author, TITLE, DESCRIPTION, PRICE);
    }

    protected static Ad generateAd(User author, String title) {
        return generateAd(author, title, DESCRIPTION, PRICE);
    }
    protected static Ad generateAd(User author,
                                   String title,
                                   String description, int price) {
        Ad ad = new Ad();
        ad.setPk(PK);
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setAuthor(author);
        ad.setPrice(price);
        ad.setImage(IMAGE);

        return ad;
    }

    protected static CreateOrUpdateAd generateCreateOrUpdateAd() {
        return generateCreateOrUpdateAd(TITLE, DESCRIPTION, PRICE);
    }

    protected static CreateOrUpdateAd generateCreateOrUpdateAd(
            String title, String description, int price) {
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setPk(PK);
        createOrUpdateAd.setDescription(description);
        createOrUpdateAd.setPrice(price);
        createOrUpdateAd.setTitle(title);
        return createOrUpdateAd;
    }

    //given
    public static Stream<List<Ad>> streamAds() {
        User author = new User();
        return Stream.of(
                new ArrayList<>(0),
                List.of(generateAd(author, TITLE + 0)),
                List.of(
                        generateAd(author, TITLE + 0),
                        generateAd(author, TITLE + 1),
                        generateAd(author, TITLE + 2)
                )
        );
    }

    public static Stream<Arguments> streamAdsDto() {

        return streamAds()
                .map(ads -> Arguments.of(
                        Mappers.getMapper(AdMapper.class).adsToAdsDto(ads),
                        ads)
                );
    }
}
