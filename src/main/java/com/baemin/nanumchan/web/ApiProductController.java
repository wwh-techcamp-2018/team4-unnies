package com.baemin.nanumchan.web;

import com.baemin.nanumchan.dto.ProductDto;
import com.baemin.nanumchan.service.ProductService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> upload(@Valid ProductDto productDto) {
        return ResponseEntity.created(URI.create("/api/products/" + productService.create(productDto))).build();
    }
}
