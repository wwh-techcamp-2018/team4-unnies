package com.baemin.nanumchan.domain;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class UserValidationTest {

    private SignUpDTO user;

    private static Validator validator;

    @Before
    public void setUp() throws Exception {
        user = SignUpDTO.builder()
                .email("unnies@naver.com")
                .password("haha123!")
                .confirmPassword("haha123!")
                .name("강석윤")
                .phoneNumber("010-1111-2222")
                .address("서울특별시 배민동 배민아파트")
                .build();
    }

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    public void email_올바른형식() {
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);

    }

    @Test
    public void email_틀린형식() {
        user.setEmail("ab.com");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void name_올바른형식() {
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void name_틀린형식_최대_길이_초과() {
        user.setName("김수완무거북이과오러아려ㅏ너아쟈포나기가");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void name_틀린형식_최소_길이_미만() {
        user.setName("김");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void name_틀린형식_숫자포함() {
        user.setName("김1");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void name_틀린형식_특수문자포함() {
        user.setName("김$이혁진");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_올바른형식() {
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void password_틀린형식_최대_길이_초과() {
        user.setPassword("wlgkfjdkvu!@#231412414213");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_틀린형식_최소_길이_미만() {
        user.setPassword("w!3");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_특수문자_없음() {
        user.setPassword("wfhjd3333");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void password_숫자_없음() {
        user.setPassword("wfhjd!!!!");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void phoneNumber_올바른형식() {
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void phoneNumber_틀린_기지국_번호() {
        user.setPhoneNumber("000-1234-5678");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void phoneNumber_숫자가아님() {
        user.setPhoneNumber("000-1234-567a");
        Set<ConstraintViolation<SignUpDTO>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }
}