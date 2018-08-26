package com.baemin.nanumchan.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    private Order order;
    private static Validator validator;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void order_올바른형식() {
        order = Order.builder()
                .product(Product.builder().build())
                .participant(User.builder().build())
                .status(Status.ON_SHARING)
                .deliveryType(DeliveryType.BAEMIN_RIDER)
                .build();
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void order_NULL() {
        order = Order.builder()
                .product(null)
                .participant(null)
                .status(null)
                .deliveryType(null)
                .build();
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations.size()).isEqualTo(4);
    }

    @Test
    public void isCompleteSharing() {
        order = Order.builder()
                .status(Status.COMPLETE_SHARING)
                .build();

        assertThat(order.getStatus().isSharingCompleted()).isTrue();
    }

    @Test
    public void isNotCompleteSharing() {
        order = Order.builder()
                .status(Status.ON_SHARING)
                .build();
        assertThat(order.getStatus().isSharingCompleted()).isFalse();
    }
}
