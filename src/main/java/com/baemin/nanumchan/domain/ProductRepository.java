package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Long countByOwnerId(Long ownerId);

    Page<Product> findAllByOwnerIdOrderByIdDesc(Long ownerId, Pageable pageable);
}
