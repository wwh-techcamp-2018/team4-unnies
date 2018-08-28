package com.baemin.nanumchan.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestException extends RuntimeException {

    protected String field;

    public RestException(String message) {
        super(message);
    }

    public RestException(String field, String message) {
        super(message);
        this.field = field;
    }

}
