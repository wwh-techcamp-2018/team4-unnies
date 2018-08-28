package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.DeliveryType;
import com.baemin.nanumchan.dto.OrderDTO;
import com.baemin.nanumchan.dto.ReviewDTO;
import com.baemin.nanumchan.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.AcceptanceTest;
import support.builder.HtmlFormDataBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApiProductAcceptanceTest extends AcceptanceTest {

    private final static String PRODUCT_URL = "/api/products";

    private LocalDateTime now;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() throws Exception {
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }

    @Test
    public void upload_성공() {
        HttpEntity<MultiValueMap<String, Object>> uploadRequest = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("categoryId", 1)
                .addParameter("files", new ClassPathResource("static/images/sample.png"))
                .addParameter("name", "이름")
                .addParameter("title", "제목")
                .addParameter("description", "요약")
                .addParameter("address", "주소")
                .addParameter("addressDetail", "상세주소")
                .addParameter("latitude", 0.0)
                .addParameter("longitude", 0.0)
                .addParameter("price", 1000)
                .addParameter("maxParticipant", 3)
                .addParameter("expireDateTime", now.plusDays(2).truncatedTo(ChronoUnit.HOURS).format(formatter))
                .addParameter("shareDateTime", now.plusDays(3).truncatedTo(ChronoUnit.HOURS).format(formatter))
                .addParameter("isBowlNeeded", false)
                .build();

        ResponseEntity<Void> uploadResponse = basicAuthTemplate().postForEntity(PRODUCT_URL, uploadRequest, Void.class);
        assertThat(uploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void upload_실패_로그인안함_빈폼() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("categoryId", null)
                .addParameter("files", null)
                .addParameter("name", null)
                .addParameter("title", null)
                .addParameter("description", null)
                .addParameter("address", null)
                .addParameter("addressDetail", null)
                .addParameter("latitude", null)
                .addParameter("longitude", null)
                .addParameter("price", null)
                .addParameter("maxParticipant", 3)
                .addParameter("expireDateTime", null)
                .addParameter("shareDateTime", null)
                .addParameter("isBowlNeeded", null)
                .build();

        ResponseEntity<Void> notAuthResponse = template.postForEntity(PRODUCT_URL, request, Void.class);
        assertThat(notAuthResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL, request, RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getErrors()).hasSize(12);
    }

    @Test
    public void uploadImage_성공() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("static/images/sample.png"))
                .build();
        ResponseEntity<Void> response = template.postForEntity(PRODUCT_URL + "/images", request, Void.class);
        assertThat(response.getHeaders().getLocation().getPath()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void order_review_성공() {
        HttpEntity<MultiValueMap<String, Object>> uploadRequest = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("categoryId", 1)
                .addParameter("files", new ClassPathResource("static/images/sample.png"))
                .addParameter("name", "이름")
                .addParameter("title", "제목")
                .addParameter("description", "요약")
                .addParameter("address", "주소")
                .addParameter("addressDetail", "상세주소")
                .addParameter("latitude", 0.0)
                .addParameter("longitude", 0.0)
                .addParameter("price", 1000)
                .addParameter("maxParticipant", 3)
                .addParameter("expireDateTime", now.plusDays(2).truncatedTo(ChronoUnit.HOURS).format(formatter))
                .addParameter("shareDateTime", now.plusDays(3).truncatedTo(ChronoUnit.HOURS).format(formatter))
                .addParameter("isBowlNeeded", false)
                .build();

        ResponseEntity<Void> uploadResponse = basicAuthTemplate().postForEntity(PRODUCT_URL, uploadRequest, Void.class);
        assertThat(uploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String productPath = uploadResponse.getHeaders().getLocation().getPath();
        assertThat(productPath).isNotEmpty();

        String[] pathSplit = productPath.split("/");
        Long productId = Long.valueOf(pathSplit[pathSplit.length - 1]);

        ResponseEntity<RestResponse> orderResponse = basicAuthTemplate().postForEntity(String.format("%s/%d/%s", PRODUCT_URL, productId, "orders"), new OrderDTO(DeliveryType.PICKUP), RestResponse.class);
        assertThat(orderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(orderResponse.getHeaders().getLocation().getPath()).isNotEmpty();
// TODO: 나눔완료 구현 후
//        ResponseEntity<RestResponse> reviewResponse = basicAuthTemplate().postForEntity(String.format("%s/%d/%s", PRODUCT_URL, productId, "reviews"), new ReviewDTO("댓글", 4.2), RestResponse.class);
//        assertThat(reviewResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(reviewResponse.getHeaders().getLocation().getPath()).isNotEmpty();
    }

}
