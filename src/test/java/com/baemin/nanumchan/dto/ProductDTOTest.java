package com.baemin.nanumchan.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDTOTest {

    private ProductDto productDto;

    private Validator validator;

    @Before
    public void setUp() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        productDto = ProductDto.builder()
                .categoryId(10L)
                .description("디스크립션")
                .expireDateTime(LocalDateTime.now().toLocalDate())
                .shareDateTime(LocalDateTime.now().toLocalDate())
                .name("이름")
                .title("제목")
                .maxParticipant(3)
                .price(1000L)
                .isBowlNeeded(false)
                .build();
    }

    @Test
    public void createProduct_이름비었음() {
        productDto.setName("");

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_카테고리없음() {
        productDto.setCategoryId(null);

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_소개비었음() {
        productDto.setDescription("");

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_제목비었음() {
        productDto.setTitle("");

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_모집인원미만() {
        productDto.setMaxParticipant(0);

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_모집인원초과() {
        productDto.setMaxParticipant(7);

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_마감시간보다과거() {
        productDto.setExpireDateTime(LocalDateTime.of(2018, 8,15,15, 00).toLocalDate());

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void createProduct_나눔시간이현재보다과거() {
        productDto.setShareDateTime(LocalDateTime.of(2018, 8,15,15, 00).toLocalDate());

        Set<ConstraintViolation<ProductDto>> constraintViolations = validator.validate(productDto);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }
}
