package com.baemin.nanumchan.product.service;

import com.baemin.nanumchan.product.domain.ProductImage;
import com.baemin.nanumchan.product.domain.ProductImageRepository;
import com.baemin.nanumchan.product.domain.cloud.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private S3Uploader s3Uploader;

    @Autowired
    private ProductImageRepository productImageRepository;

    public void uploadImage(List<MultipartFile> multipartFiles) {
        System.out.println(multipartFiles.get(0));
        List<ProductImage> productImages = multipartFiles.stream()
                .map(s3Uploader::upload)
                .map(ProductImage::new)
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImages);
    }

}
