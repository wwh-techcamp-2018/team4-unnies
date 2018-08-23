package com.baemin.nanumchan.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private Product product;
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
    public void calculateStatus_기간만료() {
        product = Product.builder()
                .expireDateTime(now.minusDays(1))
                .maxParticipant(5)
                .build();
        assertThat(product.calculateStatus(5)).isEqualTo(Status.EXPIRED);
    }

    @Test
    public void calculateStatus_모집완료() {
        product = Product.builder()
                .expireDateTime(now.plusDays(1))
                .maxParticipant(5)
                .build();
        assertThat(product.calculateStatus(5)).isEqualTo(Status.FULL_PARTICIPANTS);
    }

    @Test
    public void calculateStatus_모집중() {
        product = Product.builder()
                .expireDateTime(now.plusDays(1))
                .maxParticipant(5)
                .build();
        assertThat(product.calculateStatus(4)).isEqualTo(Status.ON_PARTICIPATING);
    }
}
