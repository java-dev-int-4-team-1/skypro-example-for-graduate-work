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
public class UserService implements GetCurrentUser {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public boolean setPasswordService(NewPassword newPassword) {
        if (newPassword.getCurrentPassword().equals(getCurrentUser().getPassword())) {
            User user = getCurrentUser();
            userMapper.updateNewPassword(newPassword, user);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUser(UpdateUser updateUser) {
        User user = getCurrentUser();
        userMapper.updateUser(updateUser, user);
        userRepository.save(user);
        return true;
    }

    public UserDto getUser() {
        return userMapper.userEntityToUserDTO(getCurrentUser());
    }

    public boolean updateImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            User user = getCurrentUser();
            userMapper.updateImage(multipartFile.getBytes().toString(), user);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        org.springframework.security.core.Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User currentUserFromRepository = userRepository.findByEmail(currentUser);
        return currentUserFromRepository;
    }

}
