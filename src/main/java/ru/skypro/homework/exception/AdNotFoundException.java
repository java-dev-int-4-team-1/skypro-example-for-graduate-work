package ru.skypro.homework.exception;

import ru.skypro.homework.entity.Ad;

public class AdNotFoundException extends EntityNotFoundException {
    public AdNotFoundException (int id) {
        super(Ad.class, id);
    }

}
