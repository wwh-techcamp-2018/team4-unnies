package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.Category;
import com.baemin.nanumchan.domain.CategoryRepository;
import com.baemin.nanumchan.dto.ProductDto;
import common.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

    private Category newCategory;
    private ProductDto productDto;


    @Before
    public void setUp() throws Exception {
        Category category = Category.builder()
                .name("Good")
                .build();

        newCategory = categoryRepository.save(category);

        productDto = ProductDto.builder()
                .categoryId(newCategory.getId())
                .name("이름")
                .price(1000L)
                .title("제목")
                .description("디스크립션")
                .maxParticipant(3)
                .isBowlNeeded(false)
                .expireDateTime(LocalDateTime.now().toLocalDate())
                .shareDateTime(LocalDateTime.now().toLocalDate())
                .build();
    }

    @Test
    public void upload() {
        ResponseEntity<Void> response = template.postForEntity("/api/products", productDto, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation().getPath()).isNotEmpty();
    }

    @Test
    public void upload_유효하지않은필드에러메시지_이름없음() {
        productDto.setName(null);

        ResponseEntity<RestResponse> response = template.postForEntity("/api/products", productDto, RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody().getError().size()).isEqualTo(1);
    }

    @Test
    public void upload_카테고리없음() {
        productDto.setCategoryId(-1L);

        ResponseEntity<RestResponse> response = template.postForEntity("/api/products", productDto, RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getError().size()).isEqualTo(1);
    }
}
