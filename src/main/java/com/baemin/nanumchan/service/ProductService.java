package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.domain.cloud.S3Uploader;
import com.baemin.nanumchan.dto.OrderDTO;
import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.dto.ProductDetailDTO;
import com.baemin.nanumchan.dto.ReviewDTO;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
    private LocationRepository locationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public Product create(User user, ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(EntityNotFoundException::new);

        List<ProductImage> productImages = productImageRepository.saveAll(
                productDTO.getFiles()
                        .stream()
                        .map(s3Uploader::upload)
                        .map(ProductImage::new)
                        .collect(Collectors.toList())
        );

        Location location = locationRepository.save(productDTO.getLocation());

        Product product = productDTO.toEntity(category, productImages, location, user);

        return productRepository.save(product);
    }

    public String uploadImage(MultipartFile file) {
        return s3Uploader.upload(file);
    }

    public ProductDetailDTO getProductDetailDTO(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        Integer orderCount = (int) (long) orderRepository.countByProduct(product);

        Double ownerRating = reviewRepository.getAvgRatingByWriterId(product.getOwner().getId()).orElse(ZERO);

        return ProductDetailDTO.builder()
                .product(product)
                .orderCount(orderCount)
                .status(product.calculateStatus(orderCount))
                .ownerRating(ownerRating)
                .build();
    }

    public Order order(Long productId, OrderDTO orderDTO, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        Integer orderCount = (int) (long) orderRepository.countByProduct(product);

        Status status = product.calculateStatus(orderCount);

        if (status.equals(Status.ON_PARTICIPATING)) {
            return orderRepository.save(
                    Order.builder()
                            .deliveryType(orderDTO.getDeliveryType())
                            .product(product)
                            .participant(user)
                            .build()
            );
        }
        throw new UnAuthenticationException("You can't order because of " + status.name());
    }

    public Review uploadReview(User user, Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        Order order = orderRepository.findByParticipantIdAndProductId(user.getId(), productId)
                .orElseThrow(EntityNotFoundException::new);

        if (order.isCompleteSharing()) {
            Review review = reviewDTO.toEntity(product, user, reviewDTO);
            return reviewRepository.save(review);
        }
        throw new UnAuthenticationException("권한이 없습니다.");
    }

    public Page<Review> getReviews(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        return reviewRepository.findAllByChefOrderByIdDesc(product.getOwner(), pageable);
    }

}
