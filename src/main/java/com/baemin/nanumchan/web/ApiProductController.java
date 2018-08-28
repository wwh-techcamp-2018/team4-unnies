package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.Order;
import com.baemin.nanumchan.domain.Review;
import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.OrderDTO;
import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.dto.ReviewDTO;
import com.baemin.nanumchan.security.LoginUser;
import com.baemin.nanumchan.service.ProductService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ApiProductController {

    @Resource(name = "productService")
    private ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@LoginUser User user, @Valid ProductDTO productDTO) {
        return ResponseEntity.created(URI.create("/products/" + productService.create(user, productDTO).getId())).build();
    }

    @PostMapping(path = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadEditorImage(MultipartFile file) {
        return ResponseEntity.created(URI.create(productService.uploadImage(file))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> getProductDetailInfo(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(productService.getProductDetailDTO(id)));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<RestResponse> createOrder(@LoginUser User user, @Valid @RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        Order order = productService.createOrder(id, orderDTO, user);
        return ResponseEntity.created(URI.create("/products/" + id + "/order/" + order.getId())).body(RestResponse.success(productService.getProductDetailDTO(id)));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<RestResponse> getReviews(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(productService.getReviews(id, pageable)));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<RestResponse> createReview(@LoginUser User user, @PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO, Pageable pageable) {
        Review review = productService.createReview(user, id, reviewDTO);
        return ResponseEntity.created(URI.create("/products/" + id + "/review/" + review.getId()))
                .body(RestResponse.success(productService.getReviews(id, pageable)));
    }

    @GetMapping
    public ResponseEntity<RestResponse> getNearProducts(
            @RequestParam(defaultValue = "0") Double longitude, @RequestParam(defaultValue = "0") Double latitude
            , @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(RestResponse.success(productService.getNearProducts(longitude, latitude, offset, limit)));
    }
}