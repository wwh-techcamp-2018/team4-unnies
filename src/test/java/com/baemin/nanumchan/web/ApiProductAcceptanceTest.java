package com.baemin.nanumchan.web;

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

//    @Test
//    public void order_review_성공() {
//        HttpEntity<MultiValueMap<String, Object>> uploadRequest = HtmlFormDataBuilder
//                .multipartFormData()
//                .addParameter("categoryId", 1)
//                .addParameter("files", new ClassPathResource("static/images/sample.png"))
//                .addParameter("name", "이름")
//                .addParameter("title", "제목")
//                .addParameter("description", "요약")
//                .addParameter("address", "주소")
//                .addParameter("addressDetail", "상세주소")
//                .addParameter("latitude", 0.0)
//                .addParameter("longitude", 0.0)
//                .addParameter("price", 1000)
//                .addParameter("maxParticipant", 3)
//                .addParameter("expireDateTime", now.plusDays(2).truncatedTo(ChronoUnit.HOURS).format(formatter))
//                .addParameter("shareDateTime", now.plusDays(3).truncatedTo(ChronoUnit.HOURS).format(formatter))
//                .addParameter("isBowlNeeded", false)
//    }

//    /*
//        TODOs : 오늘꺼임
//        샘플 테스트니 추후에 없앨것!
//    */
//    @Test
//    public void order_실패_수령방법선택X() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/7/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void order_실패_로그인X() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = template.postForEntity(PRODUCT_URL + "/7/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void order_실패_존재X() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/100/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void order_실패_본인() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/7/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void order_실패_이미신청() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/8/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void order_실패_모집기간X() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/9/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void order_실패_모집꽉참() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/10/orders", orderDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void order_성공() {
//        OrderDTO orderDTO = OrderDTO.builder()
//                .deliveryType(DeliveryType.BAEMIN_RIDER)
//                .build();
//        ResponseEntity<Void> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/13/orders", orderDTO, Void.class);
//
//        log.info("success : {}", response.getHeaders().getLocation().getPath());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//    }
//
//    // 리뷰 등록
//    @Test
//    public void uploadReview_실패_로그인X() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("이거 맛있었어요 쵝오에요?")
//                .rating(4.2)
//                .build();
//
//        ResponseEntity<RestResponse> response = template.postForEntity(PRODUCT_URL + "/2/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void uploadReview_실패_답글X() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("")
//                .rating(4.2)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/2/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void uploadReview_실패_점수범위벗어남() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("")
//                .rating(6.0)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/2/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void uploadReview_실패_상품존재X() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("안녕하세요")
//                .rating(5.0)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/100/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void uploadReview_실패_주문존재X() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("안녕하세요")
//                .rating(5.0)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/12/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void uploadReview_실패_이미작성() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("안녕하세요")
//                .rating(5.0)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/9/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void uploadReview_실패_나눔완료X_test() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("안녕하세요")
//                .rating(5.0)
//                .build();
//
//        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/8/reviews", reviewDTO, RestResponse.class);
//
//        log.info("error : {}", response.getBody().getErrors());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//    @Test
//    public void uploadReview_성공_test() {
//        ReviewDTO reviewDTO = ReviewDTO.builder()
//                .comment("안녕하세요")
//                .rating(5.0)
//                .build();
//
//        ResponseEntity<Void> response = basicAuthTemplate().postForEntity(PRODUCT_URL + "/11/reviews", reviewDTO, Void.class);
//
//        log.info("success : {}", response.getHeaders().getLocation().getPath());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//    }
}

