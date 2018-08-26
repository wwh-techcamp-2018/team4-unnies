package com.baemin.nanumchan.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTest {

    private Review review;
    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void review_올바른형식() {
        review = Review.builder()
                .chef(User.builder().build())
                .writer(User.builder().build())
                .product(Product.builder().build())
                .comment("안녕하세요")
                .rating(5.0)
                .build();
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void review_빈값() {
        review = Review.builder()
                .chef(null)
                .writer(null)
                .product(null)
                .comment("")
                .rating(null)
                .build();
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        assertThat(constraintViolations.size()).isEqualTo(5);
    }

    @Test
    public void review_유효에러() {
        review = Review.builder()
                .chef(null)
                .writer(null)
                .product(null)
                .comment("")
                .rating(-0.1)
                .build();
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        assertThat(constraintViolations.size()).isEqualTo(5);
    }
}
