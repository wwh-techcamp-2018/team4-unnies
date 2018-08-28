package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    int countByOwnerId(Long ownerId);

    List<Product> findAllByOwnerId(Long ownerId);

    Page<Product> findAllByOwnerIdOrderByIdDesc(Long ownerId, Pageable pageable);

    Optional<Product> findByOwnerId(Long ownerId);
}
