package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class AdMapper {

    @Value("${realm.img}")
    private String realmImg;

    @Value("${realm.ads}")
    private String realmAds;


    public int map(User user) {

        if (user == null) {
            return -1;
        }
        Integer userId = user.getId();
        if (userId == null) {
            return -1;
        }
        return userId;
    }

    public String mapImage (Ad ad) {
        return "/" + realmImg + "/" + realmAds + "/" + ad.getImage();
    }

    @Mapping(source="id", target = "pk")
    @Mapping(target = "image",  expression = "java( mapImage(ad) )")
    public abstract AdDto map(Ad ad);

    @Mapping(target = "authorFirstName", source="author.firstName")
    @Mapping(target = "authorLastName", source="author.lastName")
    @Mapping(target = "email", source="author.email")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "image",  expression = "java( mapImage(ad) )")
    public abstract ExtendedAd mapToExtendedAd(Ad ad);

    @Mapping(source="image.originalFilename", target = "image")
    @Mapping(source="createOrUpdateAd.pk", target = "id")
    public abstract Ad map(CreateOrUpdateAd createOrUpdateAd, MultipartFile image);

    public Ads map(Collection<Ad> ads) {
        Ads result = new Ads();
        result.setResults(
                ads.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
        result.setCount(ads.size());
        return result;
    }

}