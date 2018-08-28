package com.baemin.nanumchan.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {
    String message() default "정확한 이미지를 첨부해주세요";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
