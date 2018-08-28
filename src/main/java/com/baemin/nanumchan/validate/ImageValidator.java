package com.baemin.nanumchan.validate;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private static final String IMAGE_MIME_REGEX = "image\\/(jpe?g|png)";
    private static final Long MAX_FILE_SIZE = 1_000_000L;
    private static final Integer MAX_WIDTH = 640;
    private static final Integer MAX_HEIGHT = 640;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return validateMultipartFile(file);
    }

    private boolean validateMultipartFile(MultipartFile multipartFile) {
        return Optional.of(multipartFile)
                .filter(file -> !file.isEmpty())
                .filter(this::isMimeTypeImage)
                .filter(this::lessThanMaximumAllowedSize)
                .filter(this::isValidDimension)
                .isPresent();
    }

    private boolean isMimeTypeImage(MultipartFile file) {
        return file.getContentType().matches(IMAGE_MIME_REGEX);
    }

    private boolean lessThanMaximumAllowedSize(MultipartFile file) {
        return file.getSize() <= MAX_FILE_SIZE;
    }

    private boolean isValidDimension(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return image.getWidth() <= MAX_WIDTH && image.getHeight() <= MAX_HEIGHT;
        } catch (Exception e) {
            return false;
        }
    }

}
