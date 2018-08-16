package com.baemin.nanumchan.exception;

import lombok.Getter;

@Getter
public class UnAuthenticationException extends RuntimeException {

    // TODO : exception throw 시, field와 error message를 확인할 수 있는 방법은?!
    private String field;
    private String message;

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public static UnAuthenticationException invalidEmail() {
        return new UnAuthenticationException("email", "존재하지 않는 이메일입니다");
    }

    public static UnAuthenticationException invalidPassword() {
        return new UnAuthenticationException("confirmPassword", "비밀번호가 틀립니다");
    }

}