package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Value("${realm.img}")
    private String realmImg;

    @Value("${realm.users}")
    private String realmUsers;

    public String mapImage (User user) {
        return "/" + realmImg + "/" + realmUsers + "/" + user.getImage();
    }
    @Mapping(target = "image",  expression = "java( mapImage(currentUser) )")
    public abstract UserDto userEntityToUserDTO(User currentUser);


    public abstract void updateUser(UpdateUser updateUser, @MappingTarget User currentUser);

    @Mapping(source = "username", target = "email")
    public abstract User registerFromRegisterDto(Register register);

    @Mapping(target = "image")
    public abstract void updateImage(String image, @MappingTarget User currentUser);
}
