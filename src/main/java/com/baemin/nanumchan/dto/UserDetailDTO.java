package com.baemin.nanumchan.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private String email;
    private String name;
    private String aboutMe;
    private String imageUrl;

    private Long createdReviewsCount;//내가한 리뷰
    private Long receivedReviewsCount;

    private Long createdProductsCount;//내가한 나눔
    private Long receivedProductsCount;

    private Double avgRating;
    private boolean isMine;
}
