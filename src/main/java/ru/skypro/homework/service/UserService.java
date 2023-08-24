package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CurrentUserService {

    @Value(value = "${avatars.path}")
    String avatarsDir;

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

    public boolean updateImage(MultipartFile image) throws IOException {
        if (image != null) {
            User user = getCurrentUser();
            Path filePath = Path.of(avatarsDir, user.getEmail() + "." +
                    StringUtils.getFilenameExtension(image.getOriginalFilename()));
            user.setImage(filePath.toString());
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (InputStream in = image.getInputStream();
                 OutputStream out = Files.newOutputStream(filePath, CREATE_NEW);
                 BufferedInputStream bis = new BufferedInputStream(in, 1024);
                 BufferedOutputStream bos = new BufferedOutputStream(out, 1024))
            {
                bis.transferTo(bos);
            }
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
