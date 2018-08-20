package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.domain.cloud.S3Uploader;
import com.baemin.nanumchan.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public Long create(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(EntityNotFoundException::new);

        List<ProductImage> productImages = productDTO.getFiles().stream()
                .map(s3Uploader::upload)
                .map(ProductImage::new)
                .collect(Collectors.toList());

        Product product = productDTO.toEntity(category, productImages);
        return productRepository.save(product).getId();
    }

}
