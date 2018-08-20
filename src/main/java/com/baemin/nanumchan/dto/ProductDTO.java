package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Category;
import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.ProductImage;
import com.baemin.nanumchan.exception.RestException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private static final String IMAGE_MIME_REGEX = "image\\/(jpe?g|png)";
    private static final Long MAX_FILE_SIZE = 10_000_000L;
    private static final Integer MAX_WIDTH = 640;
    private static final Integer MAX_HEIGHT = 640;

    private List<MultipartFile> files;

    @NotNull
    private Long categoryId;

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    // TODO: Location address

    @NotNull
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @Size(min = 1, max = 2000)
    private String description;

    @DecimalMin("0")
    private Integer price;

    @DecimalMin(value = "1", message = "모집인원은 1명 이상이어야 합니다.")
    @DecimalMax(value = "6", message = "모집인원은 6명 이하이어야 합니다.")
    private Integer maxParticipant;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shareDateTime;

    @NotNull
    private Boolean isBowlNeeded;

    public ProductDTO(List<MultipartFile> files, Long categoryId, String name, String title, String description, Integer price, Integer maxParticipant, LocalDateTime expireDateTime, LocalDateTime shareDateTime, boolean isBowlNeeded) {
        setFiles(files);
        this.categoryId = categoryId;
        this.name = name;
        this.title = title;
        this.description = description;
        setPrice(price);
        this.maxParticipant = maxParticipant;
        setExpireDateTime(expireDateTime);
        setShareDateTime(shareDateTime);
        this.isBowlNeeded = isBowlNeeded;
    }

    public void setPrice(Integer price) {
        if (price % 1000 != 0)
            throw new RestException("price", "가격은 1000원 단위로 입력해야 합니다");
        this.price = price;
    }

    public void setExpireDateTime(LocalDateTime expireDateTime) {
        int min = expireDateTime.getMinute();
        if (min % 10 != 0) {
            min = (min / 10 + 1) * 10;
        }

        expireDateTime = expireDateTime.withMinute(0).plusMinutes(min);
        if (expireDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new RestException("expireDateTime", "모집기간은 최소 2시간 이후로 입력해야 합니다");
        }

        this.expireDateTime = expireDateTime;
    }

    public void setShareDateTime(LocalDateTime shareDateTime) {
        if (shareDateTime.isBefore(expireDateTime)) {
            throw new RestException("shareDateTime", "나눔시간은 모집기간 이후로 입력해야 합니다");
        }
        this.shareDateTime = shareDateTime;
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
            throw RestException.UnsupportMimeType();
        }
        return true;
    }

    public boolean lessThanMaximumAllowedSize(MultipartFile file) {
        return lessThanMaximumAllowedSize(file, MAX_FILE_SIZE);
    }

    public boolean lessThanMaximumAllowedSize(MultipartFile file, Long size) {
        if (size < file.getSize()) {
            throw RestException.ExceedMaximumAllowedFileSize();
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
                throw RestException.InvalidImageDimension();
            }
        } catch (Exception e) {
            throw RestException.InvalidImageDimension();
        }
        return true;
    }

    public Product toEntity(Category category, List<ProductImage> productImages) {
        return Product.builder()
                .category(category)
                .productImages(productImages)
                .name(name)
                .title(title)
                .description(description)
                .price(price)
                .maxParticipant(maxParticipant)
                .expireDateTime(expireDateTime)
                .shareDateTime(shareDateTime)
                .isBowlNeeded(isBowlNeeded)
                .build();
    }
}
