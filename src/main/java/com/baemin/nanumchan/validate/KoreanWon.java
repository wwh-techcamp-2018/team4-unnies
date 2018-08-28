package com.baemin.nanumchan.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = KoreanWonValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KoreanWon {
    String message() default "정확한 가격을 입력해주세요";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Unit unit() default Unit.HUNDRED;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    enum Unit {
        HUNDRED(100),
        THOUSAND(1000);

        private int decimalUnit;

        Unit(int decimalUnit) {
            this.decimalUnit = decimalUnit;
        }

        public boolean isValid(int price) {
            return price % decimalUnit == 0;
        }

    }
}
