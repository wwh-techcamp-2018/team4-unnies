package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.ProductRepository;
import common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ApiProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> upload(@Valid @RequestBody Product product) {
        return ResponseEntity.created(URI.create("/api/products/" + productRepository.save(product).getId())).build();
    }
}
