package ru.skypro.homework.service;

import ru.skypro.homework.entity.CreatedByUser;
import ru.skypro.homework.entity.User;

public interface CurrentUserService {
    User getCurrentUser();
    /**
     * Throws #EditForbiddenException if user hasn't got the permission to modify or delete entity's entry.
     * It occurs if the user is not the author of the entry and if they are not admin.
     * @param entity entity to check the ability to be modified by the current user
     */
    void checkEditPermission(CreatedByUser entity);
}
