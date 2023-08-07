package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Ad {

    private Long pk;
    private Long author;

    private String title;
    private BigDecimal price;
    private String image;
}
