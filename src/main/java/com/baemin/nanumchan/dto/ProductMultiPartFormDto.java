package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.exception.ExceedMaximumAllowedFileSizeException;
import com.baemin.nanumchan.exception.InvalidImageDimensionException;
import com.baemin.nanumchan.exception.UnsupportMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMultiPartFormDto {

    private static final String IMAGE_MIME_REGEX = "image\\/(jpe?g|png)";
    private static final Long MAX_FILE_SIZE = 10_000_000L;
    private static final Integer MAX_WIDTH = 640;
    private static final Integer MAX_HEIGHT = 640;

    private List<MultipartFile> files;

    public ProductMultiPartFormDto() {

    }

    public ProductMultiPartFormDto(MultipartFile file) {
        this(Arrays.asList(file));
    }

    public ProductMultiPartFormDto(List<MultipartFile> files) {
        setFiles(files);
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = validatedMultipartFiles(files);
    }

    public List<MultipartFile> validatedMultipartFiles(List<MultipartFile> files) {
        return files.stream()
                .filter(this::isMimeTypeImage)
                .filter(this::lessThanMaximumAllowedSize)
                .filter(this::isValidDimension)
                .collect(Collectors.toList());
    }

    public boolean isMimeTypeImage(MultipartFile file) {
        return isMimeTypeImage(file, IMAGE_MIME_REGEX);
    }

    public boolean isMimeTypeImage(MultipartFile file, String pattern) {
        if (!file.getContentType().matches(pattern)) {
            throw new UnsupportMimeTypeException();
        }
        return true;
    }

    public boolean lessThanMaximumAllowedSize(MultipartFile file) {
        return lessThanMaximumAllowedSize(file, MAX_FILE_SIZE);
    }

    public boolean lessThanMaximumAllowedSize(MultipartFile file, Long size) {
        if (size < file.getSize()) {
            throw new ExceedMaximumAllowedFileSizeException();
        }
        return true;
    }

    public boolean isValidDimension(MultipartFile file) {
        return isValidDimension(file, MAX_WIDTH, MAX_HEIGHT);
    }

    public boolean isValidDimension(MultipartFile file, Integer width, Integer height) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (!(image.getWidth() < width && image.getHeight() < height)) {
                throw new InvalidImageDimensionException();
            }
        } catch (Exception e) {
            throw new InvalidImageDimensionException();
        }
        return true;
    }

}
