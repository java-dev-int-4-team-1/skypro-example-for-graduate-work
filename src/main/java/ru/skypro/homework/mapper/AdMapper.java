package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

@Mapper
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    AdDto adToAdDto(Ad ad);

    /** ToDo : define source based on User-data-members */
    @Mapping(target = "authorFirstName")
    @Mapping(target = "authorLastName")
    @Mapping(target = "email")
    @Mapping(target = "phone")
    ExtendedAd adToExtendedAd(Ad ad);


    Ad CreateOrUpdateAdToAd(CreateOrUpdateAd createOrUpdateAd);


}
