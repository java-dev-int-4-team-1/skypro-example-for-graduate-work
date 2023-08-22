package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto userEntityToUserDTO(User currentUser);

    @Mapping(source = "newPassword", target = "password")
    void updateNewPassword(NewPassword newPassword, @MappingTarget User currentUser);


    void updateUser(UpdateUser updateUser, @MappingTarget User currentUser);

    @Mapping(source = "username", target = "email")
    User registerFromRegisterDto(Register register);

    @Mapping(target = "image")
    void updateImage(String image, @MappingTarget User currentUser);
}
