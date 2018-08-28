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

    public static RestException UploadFailed() {
        return new RestException("files", "이미지 업로드에 실패했습니다");
    }

    public static RestException FileConvertFailed() {
        return new RestException("files", "이미지 변환에 실패했습니다");
    }


}
