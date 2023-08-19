package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO container for results returned with the method AdController.getAll()
 */
@Data
public class Ads {

    private int count;
    private List <AdDto> results;
}
