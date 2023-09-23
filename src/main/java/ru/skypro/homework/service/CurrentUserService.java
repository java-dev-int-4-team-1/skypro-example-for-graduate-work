package ru.skypro.homework.service;

import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.CreatedByUser;
import ru.skypro.homework.entity.User;

public interface CurrentUserService {
    /**
     * If there is not authorized request then throws UnauthorizedException
     * @return current user
     */
    @Transactional
    User getCurrentUser();
    @Transactional
    boolean hasPermission(CreatedByUser entity);
}
