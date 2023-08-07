package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Collection;

/**
 * DTO container for results returned with the method AdController.getAll()
 */
@Data
public class Ads {

    private int count;
    private Collection <Ad> results;
}
