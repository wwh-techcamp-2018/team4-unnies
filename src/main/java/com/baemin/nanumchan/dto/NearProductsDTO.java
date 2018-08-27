package com.baemin.nanumchan.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class NearProductsDTO { // repo -> client (VO가 더 적합할듯..)

    public final static int DEFAULT_RADIUS_METER = 1000; // 여기 있는게 맞나?

    private Long productId;

    private String productTitle;

    private String productImgUrl;

    private double distanceMeter;

    private String ownerName;

    private String ownerImgUrl;

    private double ownerRating;

    private long orderCnt;

    private int maxParticipant;

    private LocalDateTime expireDateTime;

    private int price;
}