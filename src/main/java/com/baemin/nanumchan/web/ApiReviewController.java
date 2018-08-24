package com.baemin.nanumchan.web;

import com.baemin.nanumchan.service.ReviewService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/reviews")
public class ApiReviewController {

    @Resource(name = "reviewService")
    private ReviewService reviewService;

    @GetMapping("/users/{id}")
    public ResponseEntity<RestResponse> getOtherReviews(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(reviewService.getOtherReviews(id, pageable)));
    }
}
