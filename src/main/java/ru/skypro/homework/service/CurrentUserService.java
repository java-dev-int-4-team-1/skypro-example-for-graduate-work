package ru.skypro.homework.service;

import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.CreatedByUser;
import ru.skypro.homework.entity.User;

public interface CurrentUserService {
    User getCurrentUser();
    @Transactional
    boolean isAuthenticated();
    @Transactional
    boolean hasPermission(CreatedByUser entity);
}
