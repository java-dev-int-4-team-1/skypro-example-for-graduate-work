package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntityToUserDTO(User user);

    User updateNewPassword(NewPassword newPassword);

    User updateUser(UpdateUser updateUser);

    User registerFromRegisterDto(Register register);
}
