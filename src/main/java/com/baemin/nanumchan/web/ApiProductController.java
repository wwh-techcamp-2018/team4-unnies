package com.baemin.nanumchan.web;

import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ApiProductController {

    @Resource(name = "productService")
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Void> upload(@Valid ProductDTO productDTO) {
        return ResponseEntity.created(URI.create("/api/products/" + productService.create(productDTO))).build();
    }

}
