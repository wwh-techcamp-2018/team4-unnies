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

    private Long reviewToCount;//내가한 리뷰
    private Long reviewFromCount;

    private Long orderToCount;//내가한 주문
    private Long orderFromCount;
}
