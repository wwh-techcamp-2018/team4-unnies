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

    @GetMapping("/{id}/orders/check")
    public ResponseEntity<RestResponse> isOrderPossible(@LoginUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(productService.getProduct(user, id)));
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

    @GetMapping("/{id}/reviews/check")
    public ResponseEntity<RestResponse> isReviewPossible(@LoginUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(productService.enableReview(user, id)));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<RestResponse> uploadReview(@LoginUser User user, @PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO, Pageable pageable) {
        Review review = productService.createReview(user, id, reviewDTO);
        return ResponseEntity.created(URI.create("/products/" + id + "/review/" + review.getId()))
                .body(RestResponse.success(productService.getReviews(id, pageable)));
    }

    @GetMapping("/{id}/orders/user")
    public ResponseEntity<RestResponse> getOrders(@LoginUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(productService.getOrders(user, id)));
    }

    @PostMapping("{id}/orders/user")
    public ResponseEntity<RestResponse> changeOrderStatus(@LoginUser User user, @PathVariable Long id, @RequestBody Long orderId) {
        Order order = productService.changeOrderStatus(user, id, orderId);
        return ResponseEntity.ok(RestResponse.success(productService.getOrders(user, id)));
    }
}