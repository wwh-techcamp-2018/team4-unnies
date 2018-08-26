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

    @Query(value = "SELECT p.id, p.created_at, p.updated_at, p.description, p.title, p.price, p.max_participant, p.expire_date_time, p.is_bowl_needed, p.name, p.share_date_time, p.category_id, p.location_id, p.owner_id " +
            "FROM (SELECT * " +
                    ", (SELECT ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(location.longitude, location.latitude)) " +
                        "FROM location " +
                        "WHERE location.id = product.location_id) AS distance_meter " +
                    "FROM product " +
                    "WHERE NOW() < product.expire_date_time) AS p " +
            "WHERE p.distance_meter < 1000 " +
            "ORDER BY p.distance_meter ASC limit :offset, :limit",
            nativeQuery = true)
    List<Product> findNearProducts(@Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT p.id, p.created_at, p.updated_at, p.description, p.title, p.price, p.max_participant, p.expire_date_time, p.is_bowl_needed, p.name, p.share_date_time, p.category_id, p.location_id, p.owner_id " +
            "FROM (SELECT * " +
            ", (SELECT ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(location.longitude, location.latitude)) " +
                "FROM location " +
                "WHERE location.id = product.location_id) AS distance_meter " +
                "FROM product " +
                "WHERE NOW() < product.expire_date_time) AS p " +
            "WHERE p.distance_meter < 1000 " +
            "AND p.category_id = :categoryId " +
            "ORDER BY p.distance_meter ASC limit :offset, :limit",
            nativeQuery = true)
    List<Product> findNearProductsByCategoryId(@Param("categoryId") Long categoryId, @Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("offset") int offset, @Param("limit") int limit);
}
