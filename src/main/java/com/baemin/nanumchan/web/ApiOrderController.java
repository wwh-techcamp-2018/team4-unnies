package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.Status;
import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.security.LoginUser;
import com.baemin.nanumchan.service.OrderService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products/{id}")
    public ResponseEntity<RestResponse> getOrders(@LoginUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(orderService.getOrders(user, id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse> changeOrderStatus(@LoginUser User user, @PathVariable Long id, @RequestBody Status status) {
        return ResponseEntity.ok(RestResponse.success(orderService.changeOrderStatus(user, id, status)));
    }
}
