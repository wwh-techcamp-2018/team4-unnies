package com.baemin.nanumchan.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UserTest {

    private User user;

    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void user_올바른형식() {
        user = User.builder()
                .email("unnies@naver.com")
                .password("haha123!")
                .name("강석윤")
                .phoneNumber("010-1111-2222")
                .address("서울특별시 배민동 배민아파트")
                .addressDetail("상세주소 동 상세호")
                .build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void user_NULL() {
        user = User.builder()
                .build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(6);
    }

    @Test
    public void user_패턴에러() {
        user = User.builder()
                .email("unniesnaver.com")
                .password("haha123")
                .name("ㄱㅅㅇ")
                .phoneNumber("0101111-2222")
                .build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        log.info("test : {}",constraintViolations.toArray());

        assertThat(constraintViolations.size()).isEqualTo(5);
    }
}