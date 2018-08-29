package com.baemin.nanumchan.exception;

public class NotAllowedException extends RestException {

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException(String field, String message) {
        super(field, message);
    }
}