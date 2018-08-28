package com.baemin.nanumchan.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UserModifyDTOTest {

    private static Validator validator;

    private static MultipartFile gifImage;
    private static MultipartFile jpgImage;

    @BeforeClass
    public static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        gifImage = new MockMultipartFile("nyan.gif", "nyan", "image/gif", new FileInputStream(new File("src/test/resources/static/images/nyan.gif")));
        jpgImage = new MockMultipartFile("dog.jpg", "dog", "image/jpg", new FileInputStream(new File("src/test/resources/static/images/dog.jpg")));
    }


    @Test
    public void aboutMe_255자_초과() {
        assertThat(validator.validateValue(UserModifyDTO.class, "aboutMe", new String(new char[255]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(UserModifyDTO.class, "aboutMe", new String(new char[255 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
    }

    @Test
    public void file_지원하지않는타입() {
        assertThat(validator.validateValue(UserModifyDTO.class, "file", gifImage).size()).isEqualTo(1);
    }

    @Test
    public void file_크기초과() {
        assertThat(validator.validateValue(UserModifyDTO.class, "file", jpgImage).size()).isEqualTo(1);
    }

    @Test
    public void file_용량초과() {
        assertThat(validator.validateValue(UserModifyDTO.class, "file", jpgImage).size()).isEqualTo(1);
    }
}
