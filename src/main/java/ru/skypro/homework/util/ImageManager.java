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
import java.util.Locale;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageManager {

    @Value("${img.path}")
    private String IMG_DIR;

    public Path getImagePath(ImageEntity imageEntity) throws IOException {
        return getImagePath(imageEntity.getClass());
    }

    public Path getImagePath(Class<? extends ImageEntity> imageEntityClass) throws IOException {

        String className = imageEntityClass.getSimpleName().toLowerCase();
        log.trace("getIamgPath(class={})", className);
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
    public String uploadImage(ImageEntity entity, MultipartFile img) {
        return uploadImage(
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
    public String uploadImage(ImageEntity entity, MultipartFile img, String filename) {
        log.trace(
                "uploadImg(entity.id={}, img.filename={}",
                entity.getId(),
                img.getOriginalFilename()
        );

        String localImageName = filename + "." +
                StringUtils.getFilenameExtension(img.getOriginalFilename()) ;
        try {
            Files.write(
                    Paths.get(
                            getImagePath(entity).toString(),
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

    public byte[] getImage(Class <? extends ImageEntity> imageEntityClass, String filename) {
        log.trace("getImg(Class={}, filename={})",
                imageEntityClass.getSimpleName(),
                filename
        );

        try {
            return Files.readAllBytes(
                    Paths.get(
                            getImagePath(imageEntityClass).toString(),
                            filename)
            );
        }
        catch (IOException e)  {
            log.error("getImg(filename={}): IOException was thrown",
                    filename, e
            );
            throw new BadImageException(filename);
        }
    }
}
