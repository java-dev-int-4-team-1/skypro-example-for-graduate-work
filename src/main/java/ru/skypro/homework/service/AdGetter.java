package ru.skypro.homework.service;

import ru.skypro.homework.entity.Ad;

public interface AdGetter {
    /**
     * throws AdNotFoundException if there is no ad entry with the id in the db
     */
    Ad getAd(Integer id);

}
