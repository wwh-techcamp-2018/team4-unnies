package com.baemin.nanumchan.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class OrderTest {

    private Order order;

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
