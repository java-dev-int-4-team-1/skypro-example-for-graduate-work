package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Comment {
    private long id;
    private long adId;
    private String text;
}