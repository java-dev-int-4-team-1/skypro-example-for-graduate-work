package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

@Mapper
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    default int map(User user) { return user.getId(); }
    AdDto adToAdDto(Ad ad);

    /** ToDo : define source based on User-data-members */
    //ToDo @Mapping(target = "authorFirstName")
    //ToDo @Mapping(target = "authorLastName")
    //ToDo @Mapping(target = "email")
    //ToDo @Mapping(target = "phone")
    ExtendedAd adToExtendedAd(Ad ad);


    Ad CreateOrUpdateAdToAd(CreateOrUpdateAd createOrUpdateAd);


}
