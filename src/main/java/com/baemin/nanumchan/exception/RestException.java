package com.baemin.nanumchan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestException extends RuntimeException {

    protected String field;

    public RestException(String field, String message) {
        super(message);
        this.field = field;
    }

    public static RestException UnsupportMimeType() {
        return new RestException("files", "JPG 혹은 PNG 파일만 업로드할 수 있습니다");
    }

    public static RestException ExceedMaximumAllowedFileSize() {
        return new RestException("files", "용량은 10MB를 초과할 수 없습니다");
    }

    public static RestException InvalidImageDimension() {
        return new RestException("files", "640x640 사이즈를 초과할 수 없습니다");
    }

    public static RestException UploadFailed() {
        return new RestException("files", "이미지 업로드에 실패했습니다");
    }

    public static RestException FileConvertFailed() {
        return new RestException("files", "이미지 변환에 실패했습니다");
    }


}
