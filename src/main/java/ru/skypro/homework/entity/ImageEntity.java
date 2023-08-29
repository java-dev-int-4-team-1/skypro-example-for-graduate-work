package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ImageEntity extends AbstractEntity {
    @Value("${imp.path}")
    protected static String imgPath;

    @Transient
    @Value("${realm.ads}")
    protected static String realmAds;

    @Transient
    @Value("${realm.users}")
    protected static String realmUsers;

    /**
     * name-part of the link to the image
     */
    private String image;

    public static String getImageSubdirFullName(String subdir) {
        return imgPath + "/" + subdir;
    }

    public String getImageSubdirFullName() {
        return getImageSubdirFullName(getImageSubdirName());
    }
    /**
     *  @return the name of the subdirectory, which
     * is specified to store the images
     * of the specific entity's instances
     */
    protected abstract String getImageSubdirName();

}
