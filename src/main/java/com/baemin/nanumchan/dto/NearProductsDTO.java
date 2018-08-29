package com.baemin.nanumchan.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class NearProductsDTO {

    public final static int DEFAULT_RADIUS_METER = 1000;

    private double distanceMeter;

    private Long productId;

    private String productTitle;

    private String productImgUrl;

    private String ownerName;

    private String ownerImgUrl;

    private double ownerRating;

    private long orderCnt;

    private int maxParticipant;

    private LocalDateTime expireDateTime;

    private int price;
}