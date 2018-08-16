package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Category;
import com.baemin.nanumchan.domain.Product;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotNull
    @Length(min = 1, message = "이름은 최소 1자 이상이어야 합니다.")
    private String name;

    // TODO: Location address

    @NotNull
    @Length(min = 1, max = 40, message = "제목은 최소 1자, 최대 40자 이하여야 합니다.")
    private String title;

    // TODO: 1000원 단위 밸리데이터
    @DecimalMin("0")
    private Long price;

    @NotNull
    @Length(min = 1, max = 2000, message = "설명은 최소 1자, 최대 2000자 이하여야 합니다.")
    private String description;

    @DecimalMin(value = "1", message = "모집인원은 1명 이상이어야 합니다.")
    @DecimalMax(value = "6", message = "모집인원은 6명 이하이어야 합니다.")
    private Integer maxParticipant;

    //TODO : 현재 일(day)단위인데 분(minute)단위로 변경하도록
    @FutureOrPresent(message = "현재 이후의 시간이어야 합니다.")
    @DateTimeFormat
    private LocalDate expireDateTime;

    @FutureOrPresent(message = "모집시간 이후의 시간이어야 합니다.")
    @DateTimeFormat
    private LocalDate shareDateTime;

    @NotNull(message = "나눔용기를 입력하여야 합니다.")
    private boolean isBowlNeeded;

    @NotNull(message = "카테고리를 입력하여야 합니다.")
    private Long categoryId;

    public Product toEntity(Category category) {
        return Product.builder()
                .name(name)
                .title(title)
                .description(description)
                .maxParticipant(maxParticipant)
                .expireDateTime(expireDateTime)
                .shareDateTime(shareDateTime)
                .isBowlNeeded(isBowlNeeded)
                .category(category)
                .build();
    }
}
