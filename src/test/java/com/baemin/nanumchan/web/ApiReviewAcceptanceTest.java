package com.baemin.nanumchan.web;

import com.baemin.nanumchan.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApiReviewAcceptanceTest extends AcceptanceTest {

    @Test
    public void get_마이페이지_유저_내가받은리뷰() {
        ResponseEntity<RestResponse> response = template.getForEntity("/api/reviews/users/1", RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
