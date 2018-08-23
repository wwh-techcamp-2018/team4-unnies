package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.Review;
import com.baemin.nanumchan.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull
    private String comment;

    @NotNull
    private Double rating;

    public Review toEntity(Product product, User user, ReviewDTO reviewDTO) {
        return Review.builder()
                .product(product)
                .writer(user)
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .build();
    }
}
