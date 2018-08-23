package support;

import com.baemin.nanumchan.dto.LoginDTO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application.yml,classpath:aws.yml")
public abstract class AcceptanceTest {

    private static final String DEFAULT_LOGIN_USER = "tech_syk@woowahan.com";
    private static final String DEFAULT_LOGIN_PASSWORD = "123456a!";

    @Autowired
    protected TestRestTemplate template;

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(LoginDTO loginUser) {
        return template.withBasicAuth(loginUser.getEmail(), loginUser.getPassword());
    }

    protected LoginDTO defaultUser() {
        return LoginDTO.builder()
                .email(DEFAULT_LOGIN_USER)
                .password(DEFAULT_LOGIN_PASSWORD)
                .build();
    }

}
