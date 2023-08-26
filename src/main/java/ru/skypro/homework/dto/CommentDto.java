package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    int author;
    String authorImage;
    String authorFirstName;
    long createdAt;

    int pk;
    String text;
}
