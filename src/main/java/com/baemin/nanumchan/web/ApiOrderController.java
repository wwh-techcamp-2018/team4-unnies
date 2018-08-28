package com.baemin.nanumchan.web;

import com.baemin.nanumchan.service.OrderService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/orders")
public class ApiOrderController {

    @Resource(name = "orderService")
    private OrderService orderService;

    @GetMapping("/users/{id}")
    public ResponseEntity<RestResponse> getReceivedProducts(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(orderService.receivedProducts(id, pageable)));
    }
}
