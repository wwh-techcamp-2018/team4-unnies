package com.baemin.nanumchan.dto;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginDTOValidationTest {

    private LoginDTO user;

    private static Validator validator;

    @Before
    public void setUp() throws Exception {
        user = LoginDTO.builder()
                .email("unnies@naver.com")
                .password("haha123!")
                .build();
    }

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    public void email_올바른형식() {
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);

    }

    @Test
    public void email_틀린형식() {
        user.setEmail("ab.com");
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void email_NULL() {
        user.setEmail(null);
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_올바른형식() {
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void password_틀린형식_최대_길이_초과() {
        user.setPassword("wlgkfjdkvu!@#231412414213");
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_틀린형식_최소_길이_미만() {
        user.setPassword("w!3");
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_특수문자_없음() {
        user.setPassword("wfhjd3333");
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_숫자_없음() {
        user.setPassword("wfhjd!!!!");
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_NULL() {
        user.setPassword(null);
        Set<ConstraintViolation<LoginDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }
}