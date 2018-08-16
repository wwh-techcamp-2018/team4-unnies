package com.baemin.nanumchan.product.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Entity
public class ProductImage extends AbstractEntity {

    private static final Long MAX_FILE_SIZE = 10_000_000L;
    private static final Integer MAX_WIDTH = 640;
    private static final Integer MAX_HEIGHT = 640;

    @Column(nullable = false)
    private String url;

    @Transient
    private MultipartFile file;

    public ProductImage() {
    }

    public ProductImage(MultipartFile file) {
        this.file = validateFile(file);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = validateFile(file);
    }

    private MultipartFile validateFile(MultipartFile file) {
        if (!file.getContentType().matches("image\\/(jpe?g|png)")) {
            throw new RuntimeException();
        }
        if (MAX_FILE_SIZE < file.getSize()) {
            throw new RuntimeException();
        }

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (MAX_WIDTH < image.getWidth()) {
                throw new RuntimeException();
            }
            if (MAX_HEIGHT < image.getHeight()) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return file;
    }

}
