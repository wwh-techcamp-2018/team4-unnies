package com.baemin.nanumchan.product.web;

import com.baemin.nanumchan.product.dto.ProductMultiPartFormDto;
import com.baemin.nanumchan.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ApiProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity create(ProductMultiPartFormDto form) {
        productService.uploadImage(form.getFiles());
        return ResponseEntity.created(URI.create("/api/products")).build();
    }

}
