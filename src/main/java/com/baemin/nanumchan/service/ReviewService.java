package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.Review;
import com.baemin.nanumchan.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> receivedReviews(Long chefId, Pageable pageable) {
        return reviewRepository.findAllByChefIdOrderByIdDesc(chefId, pageable);
    }

}
