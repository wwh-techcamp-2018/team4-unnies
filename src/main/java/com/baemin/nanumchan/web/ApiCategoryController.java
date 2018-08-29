package com.baemin.nanumchan.web;

import com.baemin.nanumchan.service.CategoryService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.http.HttpStatus;
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
    public RestResponse list() {
        return RestResponse.success(categoryService.getCategories());
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public RestResponse getNearProducts(@PathVariable Long id
            , @RequestParam(defaultValue = "0") @DecimalMin("-180.00000") @DecimalMax("180.00000") double longitude
            , @RequestParam(defaultValue = "0") @DecimalMin("-90.00000") @DecimalMax("90.00000") double latitude
            , @RequestParam(defaultValue = "0") int offset
            , @RequestParam(defaultValue = "10") int limit) {
        return RestResponse.success(categoryService.getNearProducts(id, longitude, latitude, offset, limit));
    }
}