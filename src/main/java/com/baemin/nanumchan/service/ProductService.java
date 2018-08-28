package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.domain.cloud.S3Uploader;
import com.baemin.nanumchan.dto.OrderDTO;
import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.dto.ProductDetailDTO;
import com.baemin.nanumchan.dto.ReviewDTO;
import com.baemin.nanumchan.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Double ZERO = 0.0;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private S3Uploader s3Uploader;

    @Transactional
    public Product create(User user, ProductDTO productDTO) {
        Product productBeforeSave = productDTO.toEntity();
        productBeforeSave.setOwner(user);
        productBeforeSave.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(EntityNotFoundException::new));

        Product product = productRepository.save(productBeforeSave);

        productImageRepository.saveAll(
                productDTO.getFiles()
                        .stream()
                        .map(s3Uploader::upload)
                        .map(url -> new ProductImage(url))
                        .collect(Collectors.toList())
        );

        return product;
    }

    public String uploadImage(MultipartFile file) {
        return s3Uploader.upload(file);
    }

    public ProductDetailDTO getProductDetailDTO(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        int orderCount = orderRepository.countByProductId(productId);

        Double ownerRating = reviewRepository.getAvgRatingByWriterId(product.getOwner().getId()).orElse(ZERO);

        return ProductDetailDTO.builder()
                .product(product)
                .orderCount(orderCount)
                .status(product.calculateStatus(orderCount))
                .ownerRating(ownerRating)
                .build();
    }

    public Order createOrder(Long productId, OrderDTO orderDTO, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        int orderCount = orderRepository.countByProductId(productId);

        Status status = product.calculateStatus(orderCount);

        if (!status.canOrder()) {
            throw new RestException(status.getMessage());
        }

        return orderRepository.save(
                Order.builder()
                        .deliveryType(orderDTO.getDeliveryType())
                        .product(product)
                        .participant(user)
                        .status(status)
                        .build()
        );
    }

    public Review createReview(User user, Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        Order order = orderRepository.findByParticipantIdAndProductId(user.getId(), productId)
                .orElseThrow(EntityNotFoundException::new);

        int orderCount = orderRepository.countByProductId(productId);

        Status status = product.calculateStatus(orderCount);

        if (!status.isSharingCompleted()) {
            throw new RestException(status.getMessage());
        }

        Review review = reviewDTO.toEntity(product, user, reviewDTO);
        return reviewRepository.save(review);
    }

    public Page<Review> getReviews(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        return reviewRepository.findAllByChefOrderByIdDesc(product.getOwner(), pageable);
    }

}
