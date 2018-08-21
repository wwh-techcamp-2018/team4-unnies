package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.security.LoginUser;
import com.baemin.nanumchan.service.ProductService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.created(URI.create("/api/products/" + productService.create(user, productDTO).getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> getProductDetailInfo(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(productService.getProductDetailDTO(id)));
    }

    @PostMapping("/{id}/order")
    public ResponseEntity<Void> order(@LoginUser User user, @Valid @RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        Order order = productService.order(id, orderDTO, user);
        return ResponseEntity.created(URI.create("/api/products/" + id + "/order/" + order.getId())).build();
    }
}
