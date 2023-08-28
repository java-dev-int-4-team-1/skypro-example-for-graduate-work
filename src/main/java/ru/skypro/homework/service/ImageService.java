package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.util.ImageManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageManager imageManager;

    public byte[] getUserImage(String filename) {
        log.trace("getUserImage(filename={})", filename);
        return imageManager.getImage(User.class, filename);
    }

    public byte[] getAdImage(String filename) {
        log.trace("getAdImage(filename={})", filename);
        return imageManager.getImage(Ad.class, filename);
    }
}
