package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.CreatedByUser;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.EditForbiddenException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.util.ImageManager;

@Slf4j
@Service
public class UserService implements CurrentUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ImageManager imageManager;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       ImageManager imageManager,
                       PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageManager = imageManager;
        this.encoder = encoder;
    }

    public boolean setNewPassword(NewPassword newPassword) {
        User user = getCurrentUser();
        if (encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void updateUser(UpdateUser updateUser) {
        User user = getCurrentUser();
        userMapper.updateUser(updateUser, user);
        userRepository.save(user);
    }

    public UserDto getUser() {
        return userMapper.userEntityToUserDTO(getCurrentUser());
    }

    public void updateImage(MultipartFile image){
        User user = getCurrentUser();
        String prevImage = user.getImage();
        user.setImage(imageManager.uploadImage(user, image));
        userRepository.save(user);
        imageManager.deleteImage(user, prevImage);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findByEmail(name);
    }

    @Override
    public void checkEditPermission(CreatedByUser entity) {

        User currentUser = getCurrentUser();
        int currentUserId = currentUser.getId();
        int authorId = entity.getAuthor().getId();

        log.trace("--verifyEditPermission(author.id={}, current-user.id={})",
                authorId,  currentUserId);

        if(currentUser.getRole() != Role.ADMIN && currentUserId != authorId) {
            throw new EditForbiddenException(currentUser, authorId);
        }

    }

}
