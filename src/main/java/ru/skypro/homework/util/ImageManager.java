package ru.skypro.homework.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.BadImageException;

import java.io.FileNotFoundException;
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

    @Value("${img.subdir.users}")
    private String USERS_SUBDIR;

    @Value("${img.subdir.ads}")
    private String ADS_SUBDIR;

    public Path getImagePath(ImageEntity imageEntity) throws IOException {
        if (imageEntity instanceof Ad)
            return getImagePath(ADS_SUBDIR);
        if (imageEntity instanceof User)
            return getImagePath(USERS_SUBDIR);
        log.error("There is no directory defined for images of {}",
                imageEntity.getClass());
        throw new FileNotFoundException();
    }

    public Path getImagePath(String subdir) throws IOException {
        log.trace("getImagPath(subdir={})", subdir);

        return validatePath(Path.of(IMG_DIR + "/" + subdir));
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
                entity.getId() + "-" + img.getOriginalFilename() );
    }

    /** Saves the image to the local directory.
     * If there are any faults during the writing image file
     * then BadImageException is thrown.
     * @param targetFilename supposed local filename without extension
     * @return  local filename with the extension
     */
    public String uploadImage(ImageEntity entity, MultipartFile img, String targetFilename) {
        log.trace(
                "uploadImg(entity.id={}, img.filename={}, targetFilename={}",
                entity.getId(),
                img.getOriginalFilename(),
                targetFilename
        );

        try {
            Files.write(
                    Paths.get(
                            getImagePath(entity).toString(),
                            targetFilename),
                    img.getBytes()
            );
        }
        catch (IOException e)  {
            log.error("uploadImg({}, target filename={}): IOException was thrown",
                    entity, targetFilename, e
            );
            throw new BadImageException(img.getOriginalFilename());
        }
        return targetFilename;
    }

    public byte[] getImage(String subdir, String filename) {
        log.trace("getImg(subdir={}, filename={})",
                subdir,
                filename
        );

        try {
            return Files.readAllBytes(
                    Paths.get(
                            getImagePath(subdir).toString(),
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
