package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    int countByOwnerId(Long ownerId);

    Page<Product> findAllByOwnerIdOrderByIdDesc(Long ownerId, Pageable pageable);

    // JPA와는 맞지 않는 구조, 장기적인 측면에서는 JDBC Template 권장
    @Query(value = "SELECT p.id, p.created_at, p.updated_at, p.title, p.owner_id, p.max_participant, p.expire_date_time, p.price, p.longitude, p.latitude, p.owner_id, p.category_id, p.name, p.description, p.address, p.address_detail, p.share_date_time, p.is_bowl_needed " +
            "FROM (SELECT *, ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(product.longitude, product.latitude)) AS distance_meter " +
                "FROM product " +
                "WHERE NOW() < product.expire_date_time) AS p " +
            "WHERE p.distance_meter < :radiusMeter " +
            "ORDER BY p.distance_meter ASC limit :offset, :limit",
            nativeQuery = true)
    List<Product> findNearProducts(@Param("radiusMeter") int radiusMeter, @Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT p.id, p.created_at, p.updated_at, p.title, p.owner_id, p.max_participant, p.expire_date_time, p.price, p.longitude, p.latitude, p.owner_id, p.category_id, p.name, p.description, p.address, p.address_detail, p.share_date_time, p.is_bowl_needed " +
            "FROM (SELECT *, ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(product.longitude, product.latitude)) AS distance_meter " +
                "FROM product " +
                "WHERE NOW() < product.expire_date_time) AS p " +
            "WHERE p.distance_meter < :radiusMeter " +
            "AND p.category_id = :categoryId " +
            "ORDER BY p.distance_meter ASC limit :offset, :limit",
            nativeQuery = true)
    List<Product> findNearProductsByCategoryId(@Param("categoryId") Long categoryId, @Param("radiusMeter") int radiusMeter, @Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("offset") int offset, @Param("limit") int limit);
}
