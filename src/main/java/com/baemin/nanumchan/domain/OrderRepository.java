package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    int countByProductId(Long productId);

    Optional<Order> findByParticipantIdAndProductId(Long participantId, Long productId);

    int countByParticipantId(Long participantId);

    Page<Order> findAllByParticipantIdOrderByIdDesc(Long participantId, Pageable pageable);
}
