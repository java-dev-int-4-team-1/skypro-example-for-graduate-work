package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.util.ImageManager;

@Slf4j
@Service
public class UserService implements CurrentUserService {

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ImageManager imageManager;

    private final PasswordEncoder encoder;

    public UserService(UserDetailsService userDetailsService,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       ImageManager imageManager,
                       PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageManager = imageManager;
        this.encoder = encoder;
    }

    public boolean setPasswordService(NewPassword newPassword) {
        User user = getCurrentUser();
        if (newPassword.getCurrentPassword().equals(user.getPassword())) {
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

    public boolean updateImage(MultipartFile image){
        if (image != null) {
            User user = getCurrentUser();
            user.setImage(imageManager.uploadImg(user, image));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User currentUserFromRepository = userRepository.findByEmail(currentUser);
        return currentUserFromRepository;
    }

}
