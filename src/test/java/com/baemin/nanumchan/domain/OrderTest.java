package com.baemin.nanumchan.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
    public void isCompleteSharing_성공() {
        order = new Order();
        order.changeShareStatus(Status.COMPLETE_SHARING);
        assertThat(order.isCompleteSharing()).isTrue();
    }

    @Test
    public void isCompleteSharing_실패() {
        order = new Order();
        assertThat(order.isCompleteSharing()).isFalse();
    }
}
