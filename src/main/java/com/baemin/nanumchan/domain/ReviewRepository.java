package com.baemin.nanumchan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByWriterOrderByIdDesc(User writer, Pageable pageRequest);

    @Query(
            value = "Select avg(r.rating) From Review r where r.writer_id = ?",
            nativeQuery = true)
    Double getAvgRatingByWriterId(Long writerId);
}
