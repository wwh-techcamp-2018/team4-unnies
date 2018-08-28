package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.dto.OrderDTO;
import com.baemin.nanumchan.dto.ProductDTO;
import com.baemin.nanumchan.dto.ProductDetailDTO;
import com.baemin.nanumchan.dto.ReviewDTO;
import com.baemin.nanumchan.exception.NotAllowedException;
import com.baemin.nanumchan.dto.NearProductsDTO;
import com.baemin.nanumchan.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ImageStorage imageStorage;

    @Transactional
    public Product create(User user, ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        product.setOwner(user);
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(EntityNotFoundException::new));
        product.setProductImages(
                productImageRepository.saveAll(
                        productDTO.getFiles()
                                .stream()
                                .map(imageStorage::upload)
                                .map(ProductImage::new)
                                .collect(Collectors.toList())
                )
        );

        return productRepository.save(product);
    }

    public String uploadImage(MultipartFile file) {
        return imageStorage.upload(file);
    }

    public ProductDetailDTO getProductDetailDTO(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다"));

        Double ownerRating = reviewRepository.getAvgRatingByChefId(product.getOwner().getId()).orElse(ZERO);

        return ProductDetailDTO.builder()
                .product(product)
                .ownerRating(ownerRating)
                .build();
    }

    @Transactional
    public Order createOrder(Long productId, OrderDTO orderDTO, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다"));

        Order order = orderRepository.save(orderDTO.toEntity(user, product));

        product.getOrders().add(order);
        productRepository.save(product);

        return order;
    }

    public Page<Review> getReviews(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        return reviewRepository.findAllByChefOrderByIdDesc(product.getOwner(), pageable);
    }

    public Review createReview(User user, Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다"));

        if (reviewRepository.existsByWriterAndProduct(user, product)) {
            throw new NotAllowedException("이미 리뷰를 등록하였습니다");
        }

        return reviewRepository.save(reviewDTO.toEntity(user, product));
    }

    public List<NearProductsDTO> getNearProducts(Double longitude, Double latitude, int offset, int limit) {
        List<Product> products = productRepository.findNearProducts(NearProductsDTO.DEFAULT_RADIUS_METER, longitude, latitude, offset, limit);
        return products.stream()
                .map(p -> NearProductsDTO.builder()
                        .distanceMeter(Math.floor(DistanceUtils.distanceInMeter(p.getLatitude(), p.getLongitude(), latitude, longitude)))
                        .productId(p.getId())
                        .productTitle(p.getTitle())
                        .productImgUrl(p.getProductImages().stream().findFirst().isPresent() ? p.getProductImages().get(0).getUrl() : null)
                        .ownerName(p.getOwner().getName())
                        .ownerImgUrl(p.getOwner().getImageUrl())
                        .ownerRating(reviewRepository.getAvgRatingByChefId(p.getOwner().getId()).orElse(ZERO))
                        .orderCnt(orderRepository.countByProductId(p.getId()))
                        .maxParticipant(p.getMaxParticipant())
                        .expireDateTime(p.getExpireDateTime())
                        .price(p.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
