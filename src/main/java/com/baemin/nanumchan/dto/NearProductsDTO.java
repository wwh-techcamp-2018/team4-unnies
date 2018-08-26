package com.baemin.nanumchan.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class NearProductsDTO {

    private String productTitle;

    private String productImgUrl;

    private Double distanceMeter;

    private String userName;

    private String userImgUrl;

    private int orderCnt;

    private int maxParticipant;

    private LocalDateTime expireDateTime;

    private int price;
}