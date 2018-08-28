package com.baemin.nanumchan.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewDTOValidationTest {

    private ReviewDTO reviewDTO;
    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void reviewDTO_올바른형식() {
        reviewDTO = ReviewDTO.builder()
                .comment("안녕하세요안녕하세요")
                .rating(5.0)
                .build();
        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void reviewDTO_빈값() {
        reviewDTO = ReviewDTO.builder()
                .comment("")
                .rating(null)
                .build();
        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        assertThat(constraintViolations.size()).isEqualTo(2);
    }

    @Test
    public void reviewDTO_유효에러() {
        reviewDTO = ReviewDTO.builder()
                .comment("안녕하세요")
                .rating(6.0)
                .build();
        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        assertThat(constraintViolations.size()).isEqualTo(2);
    }
}
