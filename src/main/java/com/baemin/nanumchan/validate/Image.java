package com.baemin.nanumchan.validate;

import lombok.AllArgsConstructor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {
    String message() default "{3}B크기의 가로{4}x세로{2} 이미지가 아닙니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    AcceptType[] accept() default {};

    int size() default Integer.MAX_VALUE;

    int width() default 1920;

    int height() default 1080;

    @AllArgsConstructor
    enum AcceptType {
        JPG("image/jpeg"),
        PNG("image/png"),
        GIF("image/gif");

        private String type;

        public boolean matches(String type) {
            return this.type.matches(type);
        }
    }
}
