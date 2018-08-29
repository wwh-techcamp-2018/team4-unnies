package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    int countByProductId(Long productId);

    Optional<Order> findByParticipantIdAndProductId(Long participantId, Long productId);

    int countByParticipantId(Long participantId);

    Page<Order> findAllByParticipantIdOrderByIdDesc(Long participantId, Pageable pageable);

    boolean existsByParticipantAndProduct(User user, Product product);

    List<Order> findAllByProduct(Product product);
}
