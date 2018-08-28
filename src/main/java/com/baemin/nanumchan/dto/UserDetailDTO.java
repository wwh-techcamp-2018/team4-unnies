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

    private int createdReviewsCount;//내가한 리뷰
    private int receivedReviewsCount;

    private int createdProductsCount;//내가한 나눔
    private int receivedProductsCount;

    private Double avgRating;
    private boolean isMine;
}
