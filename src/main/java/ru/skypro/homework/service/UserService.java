package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public boolean setPasswordService(NewPassword newPassword) {
        if (newPassword.getCurrentPassword().equals(getCurrentUser().getPassword())) {
            userMapper.updateNewPassword(newPassword, getCurrentUser());
            return true;
        }
        return false;
    }

    public boolean updateUser(UpdateUser updateUser) {
        userMapper.updateUser(updateUser, getCurrentUser());
        return true;
    }

    public UserDto getUser() {
        return userMapper.userEntityToUserDTO(getCurrentUser());
    }

    public boolean updateImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            userMapper.updateImage(multipartFile.getBytes().toString(), getCurrentUser());
            return true;
        }
        return false;
    }


    private User getCurrentUser() {
        org.springframework.security.core.Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        User currentUserFromRepository = userRepository.findById(currentUser.getId());
        return currentUserFromRepository;
    }

}
