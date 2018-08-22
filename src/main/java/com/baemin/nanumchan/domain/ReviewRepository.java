package com.baemin.nanumchan.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findTop10ByWriterOrderByIdDesc(User writer);

}
