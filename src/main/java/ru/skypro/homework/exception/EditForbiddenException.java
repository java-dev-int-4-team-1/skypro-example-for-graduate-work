package ru.skypro.homework.exception;

import ru.skypro.homework.entity.User;

public class EditForbiddenException extends RuntimeException {
    public EditForbiddenException(User currentUser, int authorId) {
        super("The current user " + currentUser.getFirstName() +
                " with id=" + currentUser.getId() +
                " with role=" + currentUser.getRole() +
                " hasn't got the permission to modify entry " +
                "which belong to the user with id=" + authorId + ".");
    }
}
