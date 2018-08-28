package com.baemin.nanumchan.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KoreanWonValidator implements ConstraintValidator<KoreanWon, Integer> {

    private KoreanWon.Unit unit;
    private int min;
    private int max;

    @Override
    public void initialize(KoreanWon price) {
        unit = price.unit();
        min = price.min();
        max = price.max();
    }

    @Override
    public boolean isValid(Integer field, ConstraintValidatorContext context) {
        if (field == null) {
            return true;
        }
        if (field < 0) {
            return false;
        }
        if (field < min) {
            return false;
        }
        if (max < field) {
            return false;
        }
        return unit.isValid(field);
    }

}
