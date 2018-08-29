package com.baemin.nanumchan.validate;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Optional;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private Image.AcceptType[] accept;
    private int size;
    private int width;
    private int height;

    @Override
    public void initialize(Image constraintAnnotation) {
        this.accept = constraintAnnotation.accept();
        this.size = constraintAnnotation.size();
        this.width = constraintAnnotation.width();
        this.height = constraintAnnotation.height();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return Optional.of(multipartFile)
                .filter(file -> !file.isEmpty())
                .filter(this::isAcceptable)
                .filter(this::hasValidSize)
                .filter(this::hasValidDimension)
                .isPresent();
    }

    private boolean isAcceptable(MultipartFile file) {
        return Arrays.stream(this.accept).anyMatch(type -> type.matches(file.getContentType()));
    }

    private boolean hasValidSize(MultipartFile file) {
        return file.getSize() <= this.size;
    }

    private boolean hasValidDimension(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return image.getWidth() <= this.width && image.getHeight() <= this.height;
        } catch (Exception e) {
            return false;
        }
    }

}
