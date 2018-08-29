package com.baemin.nanumchan.exception;

public class UnAuthenticationException extends RestException {

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(String field, String message) {
        super(field, message);
    }

    public static UnAuthenticationException invalidEmail() {
        return new UnAuthenticationException("email", "존재하지 않는 이메일입니다");
    }

    public static UnAuthenticationException invalidPassword() {
        return new UnAuthenticationException("password", "비밀번호가 틀립니다");
    }

    public static UnAuthenticationException existEmail() {
        return new UnAuthenticationException("email", "이미 존재하는 이메일입니다");
    }
}