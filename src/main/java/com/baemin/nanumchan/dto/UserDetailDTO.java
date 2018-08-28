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

    private int reviewToCount;//내가한 리뷰
    private int reviewFromCount;

    private int orderToCount;//내가한 주문
    private int orderFromCount;
}
