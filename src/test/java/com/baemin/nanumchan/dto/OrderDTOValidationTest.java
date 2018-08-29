package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.DeliveryType;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDTOValidationTest {

    private OrderDTO orderDTO;
    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void orderDTO_올바른형식() {
        orderDTO = OrderDTO.builder()
                .deliveryType(DeliveryType.BAEMIN_RIDER)
                .build();
        Set<ConstraintViolation<OrderDTO>> constraintViolations = validator.validate(orderDTO);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void orderDTO_NULL() {
        orderDTO = OrderDTO.builder()
                .deliveryType(null)
                .build();
        Set<ConstraintViolation<OrderDTO>> constraintViolations = validator.validate(orderDTO);
        assertThat(constraintViolations.size()).isEqualTo(1);
    }
}
