package com.baemin.nanumchan.web;

import com.baemin.nanumchan.service.CategoryService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Validated
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
            , @RequestParam(defaultValue = "0") @DecimalMin("-180.00000") @DecimalMax("180.00000") Double longitude
            , @RequestParam(defaultValue = "0") @DecimalMin("-90.00000") @DecimalMax("90.00000") Double latitude
            , @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(RestResponse.success(categoryService.getNearProducts(id, longitude, latitude, offset, limit)));
    }
}