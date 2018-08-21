package com.baemin.nanumchan.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Long> countByProduct(Product product);

    Optional<Order> findByParticipantIdAndProductId(Long id, Long productId);
}
