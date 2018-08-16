package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.Category;
import com.baemin.nanumchan.domain.CategoryRepository;
import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.ProductRepository;
import com.baemin.nanumchan.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Long create(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(EntityNotFoundException::new);
        Product product = productDto.toEntity(category);

        return productRepository.save(product).getId();
    }
}
