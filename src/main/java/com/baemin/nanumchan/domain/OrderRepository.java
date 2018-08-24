package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Long countByProduct(Product product);

    Optional<Order> findByParticipantIdAndProductId(Long participantId, Long productId);

    Long countByParticipantId(Long participantId);

    Page<Order> findAllByParticipantIdOrderByIdDesc(Long participantId, Pageable pageable);
}
