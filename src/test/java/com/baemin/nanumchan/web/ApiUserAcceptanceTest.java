package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.SignUpDTO;
import com.baemin.nanumchan.utils.RestResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiUserAcceptanceTest extends AcceptanceTest {

    private SignUpDTO signUpDTO;

    @Before
    public void setUp() throws Exception {
        signUpDTO = SignUpDTO.builder()
                                .email("unnies@naver.com")
                                .password("haha123!")
                                .confirmPassword("haha123!")
                                .name("강석윤")
                                .phoneNumber("010-1111-2222")
                                .address("서울특별시 배민동 배민아파트")
                                .build();
    }

    @Test
    public void create() {
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users", signUpDTO, RestResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
