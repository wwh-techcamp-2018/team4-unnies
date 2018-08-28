package com.baemin.nanumchan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    //About Order
    ON_SHARING("나눔중"),
    COMPLETE_SHARING("나눔 완료"),

    //About Product Detail Info
    EXPIRED("나눔 만료 시간이 지났습니다"),
    FULL_PARTICIPANTS("나눔 신청이 마감되었습니다"),
    ON_PARTICIPATING("나눔 신청이 가능합니다");

    private String message;

    public boolean canOrder() {
        return name().equals(ON_PARTICIPATING.name());
    }

    public boolean isSharingCompleted() {
        return name().equals(COMPLETE_SHARING.name());
    }
}
