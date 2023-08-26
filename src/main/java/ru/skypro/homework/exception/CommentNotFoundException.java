package ru.skypro.homework.exception;

import ru.skypro.homework.entity.Comment;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException(int adId, int commentId) {
        super("The " + Comment.class.getName() +
                " with adId=" + adId +
                " and commentId=" + commentId +
                " is not listed in the db.");
    }

}
