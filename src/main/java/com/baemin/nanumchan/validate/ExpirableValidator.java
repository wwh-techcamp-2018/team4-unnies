package com.baemin.nanumchan.validate;

import com.baemin.nanumchan.domain.DateTimeExpirable;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
public class ExpirableValidator implements ConstraintValidator<Expirable, DateTimeExpirable> {

    private static final int HOUR_THRESHOLD = 2;

    private LocalDateTime now;

    @Override
    public void initialize(Expirable constraintAnnotation) {
        now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        int minute = now.getMinute();
        if (minute % 10 != 0) {
            now = now.withMinute(((minute / 10) + 1) * 10 % 60);
        }
    }

    @Override
    public boolean isValid(DateTimeExpirable bean, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        LocalDateTime start = bean.getStartDateTime();
        if (start == null) {
            return true;
        }

        int startMin = start.getMinute();
        if (startMin % 10 != 0) {
            context.buildConstraintViolationWithTemplate("모집 기간은 10분 단위로만 입력 가능합니다")
                    .addPropertyNode("expireDateTime")
                    .addConstraintViolation();
            return false;
        }

        if (start.isBefore(now.plusHours(HOUR_THRESHOLD))) {
            context.buildConstraintViolationWithTemplate("모집 기간은 최소 2시간 이후부터 설정 가능합니다")
                    .addPropertyNode("expireDateTime")
                    .addConstraintViolation();
            return false;
        }

        LocalDateTime end = bean.getEndDateTime();
        if (end == null) {
            return true;
        }

        int endMin = end.getMinute();
        if (endMin % 10 != 0) {
            context.buildConstraintViolationWithTemplate("나눔 시간은 10분 단위로만 입력 가능합니다")
                    .addPropertyNode("shareDateTime")
                    .addConstraintViolation();
            return false;
        }

        if (end.isBefore(start)) {
            context.buildConstraintViolationWithTemplate("나눔 시간은 모집 시간 이후로 입력해주세요")
                    .addPropertyNode("shareDateTime")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
