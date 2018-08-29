package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDetailDTOValidationTest {

    private ProductDetailDTO productDetailDTO;
    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void productDetailDTO_올바른형식() {
        productDetailDTO = ProductDetailDTO.builder()
                .product(Product.builder().build())
                .ownerRating(1.0)
                .build();
        Set<ConstraintViolation<ProductDetailDTO>> constraintViolations = validator.validate(productDetailDTO);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void productDetailDTO_NULL() {
        productDetailDTO = ProductDetailDTO.builder()
                .product(null)
                .ownerRating(null)
                .build();
        Set<ConstraintViolation<ProductDetailDTO>> constraintViolations = validator.validate(productDetailDTO);
        assertThat(constraintViolations.size()).isEqualTo(2);
    }

    @Test
    public void productDetailDTO_유효에러() {
        productDetailDTO = ProductDetailDTO.builder()
                .product(null)
                .ownerRating(-1.0)
                .build();
        Set<ConstraintViolation<ProductDetailDTO>> constraintViolations = validator.validate(productDetailDTO);
        assertThat(constraintViolations.size()).isEqualTo(2);
    }
}
