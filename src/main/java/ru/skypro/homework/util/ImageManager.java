package ru.skypro.homework.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageManager {

    @Value("${img.path}")
    private String IMG_DIR;

    private final String AD_IMG_DIR = IMG_DIR + "/ads";

    public Path getAdImgPath() throws IOException {
        log.trace("getAdImgPath, ADM_IMG_DIR={}", AD_IMG_DIR);

        return validatePath(Path.of(AD_IMG_DIR));
    }


    private Path validatePath(Path path) throws IOException {
        log.trace("validatePath(path={})", path);

        if (Files.isDirectory(path)) {
            return path;
        }
        return Files.createDirectories(path);
    }

    public void uploadAdImg(Ad ad, MultipartFile img) throws IOException {
        log.trace("uploadImg(ad, img");

        Files.write(
                Paths.get(getAdImgPath().toString(), getLocalFilename(ad, img)),
                img.getBytes()
        );
    }

    private static String getLocalFilename(Ad ad, MultipartFile img) {
        String filename = String.format(
                "%s-%d.%s",
                img.getName(),
                ad.getPk(),
                StringUtils.getFilenameExtension(img.getOriginalFilename())
        );
        log.trace("getLocalFilename(ad.pk={}, img.name={})={}", ad.getPk(), img.getName(), filename);
        return filename;
    }
}
