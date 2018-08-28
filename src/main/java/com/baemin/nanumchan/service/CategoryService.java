package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.dto.NearProductsDTO;
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
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public List<NearProductsDTO> getNearProducts(Long categoryId, Double longitude, Double latitude, int offset, int limit) {
        List<Product> products = productRepository.findNearProductsByCategoryId(categoryId, NearProductsDTO.DEFAULT_RADIUS_METER, longitude, latitude, offset, limit);
        return getNearProductDTO(products, longitude, latitude);
    }

    private List<NearProductsDTO> getNearProductDTO(List<Product> products, Double longitude, Double latitude) {
        return products.stream()
                .map(p -> NearProductsDTO.builder()
                        .productId(p.getId())
                        .productTitle(p.getTitle())
                        .productImgUrl(p.getProductImages().stream().findFirst().isPresent() ? p.getProductImages().get(0).getUrl() : null)
                        .distanceMeter(Math.floor(DistanceUtils.distanceInMeter(p.getLocation().getLatitude(), p.getLocation().getLongitude(), latitude, longitude)))
                        .ownerName(p.getOwner().getName())
                        .ownerImgUrl(p.getOwner().getImageUrl())
                        .ownerRating(reviewRepository.getAvgRatingByChefId(p.getOwner().getId()).orElse(0.0))
                        .orderCnt(orderRepository.countByProduct(p))
                        .maxParticipant(p.getMaxParticipant())
                        .expireDateTime(p.getExpireDateTime())
                        .price(p.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
