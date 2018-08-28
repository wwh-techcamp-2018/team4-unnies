package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.DateTimeExpirable;
import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.validate.Expirable;
import com.baemin.nanumchan.validate.Image;
import com.baemin.nanumchan.validate.KoreanWon;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Expirable
public class ProductDTO implements DateTimeExpirable {

    @Nullable
    @Size(max = 5)
    private List<@Image MultipartFile> files;

    @NotNull
    @Range(min = 1, max = 6)
    private Long categoryId;

    @NotNull
    @Length(min = 1, max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 100)
    private String title;

    @NotNull
    @KoreanWon(max = 1000000, unit = KoreanWon.Unit.HUNDRED)
    private Integer price;

    @NotNull
    @Length(min = 1, max = 100000)
    private String description;

    @NotNull
    @Length(min = 1, max = 50)
    private String address;

    @NotNull
    @Length(min = 1, max = 50)
    private String addressDetail;

    @NotNull
    @DecimalMin("-90.00000")
    @DecimalMax("90.00000")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.00000")
    @DecimalMax("180.00000")
    private Double longitude;

    @NotNull
    @Range(min = 1, max = 6)
    private int maxParticipant;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expireDateTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime shareDateTime;

    @NotNull
    private Boolean isBowlNeeded;

    @Override
    public LocalDateTime getStartDateTime() {
        return expireDateTime;
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return shareDateTime;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .title(title)
                .description(description)
                .price(price)
                .address(address)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .maxParticipant(maxParticipant)
                .expireDateTime(expireDateTime)
                .shareDateTime(shareDateTime)
                .isBowlNeeded(isBowlNeeded)
                .build();
    }

}
