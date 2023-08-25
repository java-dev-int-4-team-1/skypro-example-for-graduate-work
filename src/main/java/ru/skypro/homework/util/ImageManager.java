package ru.skypro.homework.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.exception.BadImageException;

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


    public Path getImgPath(ImageEntity entity) throws IOException {

        String className = entity.getClass().getSimpleName();
        log.trace("getImgPath(class={})", className);
            return validatePath(Path.of(IMG_DIR + "/" + className));
    }

    private Path validatePath(Path path) throws IOException {
        log.trace("validatePath(path={})", path);

        if (Files.isDirectory(path)) {
            return path;
        }
        return Files.createDirectories(path);
    }

    /** Saves the image to the local directory. The local filename will consist of the
     * original filename and the entity's id
     * @return the local filename
     */
    public String uploadImg(ImageEntity entity, MultipartFile img) {
        return uploadImg(
                entity,
                img,
                img.getOriginalFilename() + "-" + entity.getId());
    }

    /** Saves the image to the local directory.
     * If there are any faults during the writing image file
     * then BadImageException is thrown.
     * @param filename supposed local filename without extension
     * @return  local filename with the extension
     */
    public String uploadImg(ImageEntity entity, MultipartFile img, String filename) {
        log.trace("uploadImg(entity, img");

        String localImageName = filename + "." +
                StringUtils.getFilenameExtension(img.getOriginalFilename()) ;
        try {
            Files.write(
                    Paths.get(
                            getImgPath(entity).toString(),
                            localImageName),
                    img.getBytes()
            );
        }
        catch (IOException e)  {
            log.error("uploadImg({}, image.name={}): IOException was thrown",
                    entity, img.getOriginalFilename(), e
            );
            throw new BadImageException(img.getOriginalFilename());
        }
        return localImageName;
    }

    private static String getLocalFilename(ImageEntity entity, MultipartFile img) {
        String filename = String.format(
                "%s-%d.%s",
                img.getOriginalFilename(),
                entity.getId(),
                StringUtils.getFilenameExtension(img.getOriginalFilename())
        );
        log.trace("getLocalFilename({}.pk={}, img.name={})={}",
                entity.getClass().getSimpleName(),
                entity.getId(), img.getName(), filename);
        return filename;
    }
}
