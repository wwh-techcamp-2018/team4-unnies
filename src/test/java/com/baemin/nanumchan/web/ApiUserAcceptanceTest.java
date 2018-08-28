package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.dto.SignUpDTO;
import com.baemin.nanumchan.dto.UserModifyDTO;
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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApiUserAcceptanceTest extends AcceptanceTest {

    private SignUpDTO signUpDTO;

    private LoginDTO loginDTO;

    @Before
    public void setUp() throws Exception {
        signUpDTO = SignUpDTO.builder()
                .email("testapi@naver.com")
                .password("haha123!")
                .confirmPassword("haha123!")
                .name("강석윤")
                .phoneNumber("010-1111-2222")
                .address("서울특별시 배민동 배민아파트")
                .addressDetail("상세동 상세호")
                .build();

        loginDTO = LoginDTO.builder()
                .email("tech_syk@woowahan.com")
                .password("123456a!")
                .build();
    }

    @Test
    public void create() {
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users", signUpDTO, RestResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void create_실패() {
        signUpDTO.setEmail(null);
        signUpDTO.setPassword("haha11");
        signUpDTO.setAddress(null);
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users", signUpDTO, RestResponse.class);

        log.info("response : {}", response.getBody().getErrors());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void create_실패_비밀번호불일치() {
        signUpDTO.setConfirmPassword("haha123!!");
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users", signUpDTO, RestResponse.class);

        log.info("response : {}", response.getBody().getErrors());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void login() {
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users/login", loginDTO, RestResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void login_실패_이메일() {
        loginDTO.setEmail("unnies_test_fail@naver.com");
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users/login", loginDTO, RestResponse.class);

        log.info("response : {}", response.getBody().getErrors());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void login_실패_비밀번호() {
        loginDTO.setPassword("1234567a!");
        ResponseEntity<RestResponse> response = template.postForEntity("/api/users/login", loginDTO, RestResponse.class);

        log.info("response : {}", response.getBody().getErrors());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

//    @Test
//    public void get_마이페이지_유저활동정보() {
//        ResponseEntity<RestResponse> response = template.getForEntity("/api/users/1", RestResponse.class);
//        log.info("response : {}", response.getBody().getData());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    public void get_마이페이지_유저_내가한리뷰() {
        ResponseEntity<RestResponse> response = template.getForEntity("/api/users/1/reviews", RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

//    @Test
//    public void get_마이페이지_유저_내가한나눔() {
//        ResponseEntity<RestResponse> response = template.getForEntity("/api/users/1/products", RestResponse.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    public void modify_마이페이지_유저_프로필수정() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("id", 1L)
                .addParameter("file", new ClassPathResource("static/images/sample.png"))
                .addParameter("aboutMe", "굿바이")
                .addParameter("imageUrl", "https://333.com")
                .build();

        ResponseEntity<RestResponse> response = basicAuthTemplate().postForEntity("/api/users/1", request, RestResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData()).isNotNull();

    }
}
