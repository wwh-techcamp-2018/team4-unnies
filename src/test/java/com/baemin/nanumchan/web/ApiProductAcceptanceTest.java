package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.Category;
import com.baemin.nanumchan.domain.CategoryRepository;
import com.baemin.nanumchan.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.AcceptanceTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApiProductAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void upload() {
        Category category = Category.builder()
                .name("Good")
                .build();

        Category newCategory = categoryRepository.save(category);

        Product product = Product.builder()
                .category(newCategory)
                .description("디스크립션")
                .expireDateTime(LocalDateTime.now().toLocalDate())
                .shareDateTime(LocalDateTime.now().toLocalDate())
                .name("이름")
                .title("제목")
                .maxParticipant(3)
                .price(1000L)
                .isBowlNeeded(false)
                .build();

        ResponseEntity<Void> response = template.postForEntity("/api/products", product, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation().getPath()).isNotEmpty();
    }
}
