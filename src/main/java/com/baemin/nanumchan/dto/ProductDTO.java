package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.exception.RestException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private static final String IMAGE_MIME_REGEX = "image\\/(jpe?g|png)";
    private static final Long MAX_FILE_SIZE = 10_000_000L;
    private static final Integer MAX_WIDTH = 640;
    private static final Integer MAX_HEIGHT = 640;

    @Nullable
    private List<MultipartFile> files;

    @NotNull
    private Long categoryId;

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    @NotNull
    @Size(min = 1, max = 40)
    private String title;

    @PositiveOrZero
    private int price;

    @NotNull
    @Size(min = 1, max = 2000)
    private String description;

    @NotNull
    private String address;

    @NotNull
    private String addressDetail;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @DecimalMin(value = "1", message = "모집인원은 1명 이상이어야 합니다.")
    @DecimalMax(value = "6", message = "모집인원은 6명 이하이어야 합니다.")
    private Integer maxParticipant;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expireDateTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime shareDateTime;

    @NotNull
    private Boolean isBowlNeeded;

    public ProductDTO(List<MultipartFile> files, Long categoryId, String name, String title, int price, String description, String address, String addressDetail, Double latitude, Double longitude, Integer maxParticipant, LocalDateTime expireDateTime, LocalDateTime shareDateTime, Boolean isBowlNeeded) {
        setFiles(files);
        this.categoryId = categoryId;
        this.name = name;
        this.title = title;
        setPrice(price);
        this.description = description;
        this.address = address;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxParticipant = maxParticipant;
        setExpireDateTime(expireDateTime);
        setShareDateTime(shareDateTime);
        this.isBowlNeeded = isBowlNeeded;
    }

    public void setPrice(int price) {
        if (price % 1000 != 0)
            throw new RestException("price", "가격은 1000원 단위로 입력해야 합니다");
        this.price = price;
    }

    public void setExpireDateTime(LocalDateTime expireDateTime) {
        if (expireDateTime == null) {
            throw new RestException("expireDateTime", "모집기간을 입력해야 합니다");
        }

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
        if (expireDateTime == null) {
            throw new RestException("expireDateTime", "모집기간을 먼저 입력해야 합니다");
        }
        if (shareDateTime == null) {
            throw new RestException("shareDateTime", "나눔시간을 입력해야 합니다");
        }

        if (shareDateTime.isBefore(expireDateTime)) {
            throw new RestException("shareDateTime", "나눔시간은 모집기간 이후로 입력해야 합니다");
        }
        this.shareDateTime = shareDateTime;
    }

    public void setFiles(List<MultipartFile> files) {
        if (Optional.ofNullable(files).isPresent()) {
            files = validatedMultipartFiles(files);
        }
        this.files = files;
    }

    public List<MultipartFile> validatedMultipartFiles(List<MultipartFile> files) {
        return files.stream()
                .filter(file -> !file.isEmpty())
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

    public Location getLocation() {
        return new Location(address, addressDetail, latitude, longitude);
    }

    public Product toEntity(Category category, List<ProductImage> productImages, Location location, User user) {
        return Product.builder()
                .owner(user)
                .category(category)
                .productImages(productImages)
                .name(name)
                .title(title)
                .description(description)
                .price(price)
                .location(location)
                .maxParticipant(maxParticipant)
                .expireDateTime(expireDateTime)
                .shareDateTime(shareDateTime)
                .isBowlNeeded(isBowlNeeded)
                .build();
    }
}
