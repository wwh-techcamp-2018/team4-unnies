package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.dto.NearProductDTO;
import com.baemin.nanumchan.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<NearProductDTO> getNearProducts(Long categoryId, double longitude, double latitude, int offset, int limit) {
        List<Product> products = productRepository.findNearProductsByCategoryId(categoryId, NearProductDTO.DEFAULT_RADIUS_METER, longitude, latitude, offset, limit);
        return products.stream()
                .map(p -> p.toNearProductDTO(longitude, latitude, reviewRepository.getAvgRatingByChefId(p.getOwner().getId()).orElse(0.0)))
                .collect(Collectors.toList());
    }
}
