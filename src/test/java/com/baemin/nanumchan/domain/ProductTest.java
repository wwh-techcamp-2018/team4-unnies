package com.baemin.nanumchan.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private Product product;
    private User user;
    private LocalDateTime now;

    @Before
    public void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    public void isExpireDateTime_성공() {
        product = Product.builder()
                .expireDateTime(now.minusDays(1))
                .build();
        assertThat(product.isExpiredDateTime()).isTrue();
    }

    @Test
    public void isExpireDateTime_실패() {
        product = Product.builder()
                .expireDateTime(now.plusDays(1))
                .build();
        assertThat(product.isExpiredDateTime()).isFalse();
    }

    @Test
    public void compareMaxParticipants_성공() {
        product = Product.builder()
                .maxParticipant(5)
                .build();
        assertThat(product.compareMaxParticipants(5)).isTrue();
    }

    @Test
    public void compareMaxParticipants_실패() {
        product = Product.builder()
                .maxParticipant(5)
                .build();
        assertThat(product.compareMaxParticipants(4)).isFalse();
    }

    @Test
    public void getStatus_기간만료() {
        product = Product.builder()
                .expireDateTime(now.minusDays(1))
                .maxParticipant(5)
                .build();
        assertThat(product.getStatus()).isEqualTo(Status.EXPIRED);
    }

    @Test
    public void getStatus_모집완료() {
        product = Product.builder()
                .orders(Arrays.asList(new Order(), new Order(), new Order()))
                .expireDateTime(now.plusDays(1))
                .maxParticipant(3)
                .build();
        assertThat(product.getStatus()).isEqualTo(Status.FULL_PARTICIPANTS);
    }

    @Test
    public void getStatus_모집중() {
        product = Product.builder()
                .orders(Arrays.asList(new Order()))
                .expireDateTime(now.plusDays(1))
                .maxParticipant(4)
                .build();
        assertThat(product.getStatus()).isEqualTo(Status.ON_PARTICIPATING);
    }

    @Test
    public void isStatus_ON_PARTICIPATING_성공() {
        product = Product.builder()
                .orders(Arrays.asList(new Order()))
                .expireDateTime(now.plusDays(1))
                .maxParticipant(4)
                .build();
        assertThat(product.isStatus_ON_PARTICIPATING()).isTrue();
    }

    @Test
    public void isStatus_ON_PARTICIPATING_실패() {
        product = Product.builder()
                .orders(Arrays.asList(new Order()))
                .expireDateTime(now.plusDays(1))
                .maxParticipant(1)
                .build();
        assertThat(product.isStatus_ON_PARTICIPATING()).isFalse();
    }

    @Test
    public void isOwner_성공() {
        user = User.builder()
                .build();
        product = Product.builder()
                .owner(user)
                .build();
        assertThat(product.isOwner(user)).isTrue();
    }

    @Test
    public void isOwner_실패() {
        user = User.builder()
                .build();
        product = Product.builder()
                .owner(user)
                .build();
        assertThat(product.isOwner(User.GUEST_USER)).isTrue();
    }
}
