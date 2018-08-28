package com.baemin.nanumchan.web;

import com.baemin.nanumchan.service.CategoryService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/categories")
public class ApiCategoryController {

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponse> list() {
        return ResponseEntity.ok(RestResponse.success(categoryService.getCategories()));
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponse> getNearProducts(@PathVariable Long id
            , @RequestParam(defaultValue = "0") Double longitude, @RequestParam(defaultValue = "0") Double latitude
            , @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(RestResponse.success(categoryService.getNearProducts(id, longitude, latitude, offset, limit)));
    }
}