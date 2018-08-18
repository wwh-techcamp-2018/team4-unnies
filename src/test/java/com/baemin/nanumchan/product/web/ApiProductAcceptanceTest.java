package com.baemin.nanumchan.product.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import support.builder.HtmlFormDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application.yml,classpath:aws.yml")
public class ApiProductAcceptanceTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("files", new ClassPathResource("static/images/sample.png"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/api/products", request, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
