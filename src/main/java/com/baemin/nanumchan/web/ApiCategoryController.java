package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.CategoryRepository;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class ApiCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponse> list() {
        return ResponseEntity.ok(RestResponse.success(categoryRepository.findAll()));
    }
}