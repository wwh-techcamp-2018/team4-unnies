package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProductDTOTest {

    private Set<ConstraintViolation<ProductDTO>> constraintViolations;
    private ProductDTO productDTO;
    private Validator validator;
    private LocalDateTime now;

    private static MultipartFile gifImage;
    private static MultipartFile jpgImage;
    private static MultipartFile pngImage;

    @BeforeClass
    public static void loadFiles() throws Exception {
        gifImage = new MockMultipartFile("nyan.gif", "nyan", "image/gif", new FileInputStream(new File("src/test/resources/static/images/nyan.gif")));
        jpgImage = new MockMultipartFile("dog.jpg", "dog", "image/jpg", new FileInputStream(new File("src/test/resources/static/images/dog.jpg")));
        pngImage = new MockMultipartFile("sample.png", "sample", "image/png", new FileInputStream(new File("src/test/resources/static/images/sample.png")));
    }

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        now = LocalDateTime.now();
        productDTO = new ProductDTO();
    }

    @Test
    public void createProduct_NotNull() {
        productDTO.setCategoryId(null);
        productDTO.setName(null);
        productDTO.setTitle(null);
        productDTO.setDescription(null);
        productDTO.setIsBowlNeeded(null);
        constraintViolations = new HashSet() {{
            add(validator.validateProperty(productDTO, "categoryId"));
            add(validator.validateProperty(productDTO, "name"));
            add(validator.validateProperty(productDTO, "title"));
            add(validator.validateProperty(productDTO, "description"));
            add(validator.validateProperty(productDTO, "isBowlNeeded"));
        }};
        assertThat(constraintViolations.size()).isEqualTo(5);
    }

    @Test
    public void createProduct_SizeUnderMin() {
        productDTO.setName("");
        productDTO.setTitle("");
        productDTO.setDescription("");
        constraintViolations = new HashSet() {{
            add(validator.validateProperty(productDTO, "name"));
            add(validator.validateProperty(productDTO, "title"));
            add(validator.validateProperty(productDTO, "description"));
        }};
        assertThat(constraintViolations.size()).isEqualTo(3);
    }

    @Test
    public void createProduct_SizeOverMax() {
        productDTO.setName(new String(new char[32 + 1]).replace('\0', 'A'));
        productDTO.setTitle(new String(new char[40 + 1]).replace('\0', 'A'));
        productDTO.setDescription(new String(new char[2000 + 1]).replace('\0', 'A'));
        constraintViolations = new HashSet() {{
            add(validator.validateProperty(productDTO, "name"));
            add(validator.validateProperty(productDTO, "title"));
            add(validator.validateProperty(productDTO, "description"));
        }};
        assertThat(constraintViolations.size()).isEqualTo(3);
    }

    @Test(expected = RuntimeException.class)
    public void createProduct_Price_1000원단위아님() {
        productDTO.setPrice(1004);
    }

    @Test
    public void createProduct_MaxParticipant_InProperSize() {
        productDTO.setMaxParticipant(0);
        constraintViolations = validator.validateProperty(productDTO, "maxParticipant");
        assertThat(constraintViolations.size()).isEqualTo(1);

        productDTO.setMaxParticipant(7);
        constraintViolations = validator.validateProperty(productDTO, "maxParticipant");
        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test(expected = RuntimeException.class)
    public void createProduct_마감시간이과거() {
        productDTO.setExpireDateTime(now.minusHours(1));
    }

    @Test(expected = RuntimeException.class)
    public void createProduct_나눔시간이마감시간보다과거() {
        productDTO.setExpireDateTime(now.plusHours(2));
        productDTO.setShareDateTime(now);
    }

    @Test
    public void isJPGImage() {
        assertThat(productDTO.isMimeTypeImage(jpgImage)).isTrue();
    }

    @Test
    public void isPNGImage() {
        assertThat(productDTO.isMimeTypeImage(pngImage)).isTrue();
    }

    @Test(expected = RestException.class)
    public void notSupportGIFImage() {
        productDTO.isMimeTypeImage(gifImage);
    }

    @Test
    public void lessThanMaximumAllowedSize() {
        assertThat(productDTO.lessThanMaximumAllowedSize(jpgImage)).isTrue();
        assertThat(productDTO.lessThanMaximumAllowedSize(pngImage)).isTrue();
        assertThat(productDTO.lessThanMaximumAllowedSize(gifImage)).isTrue();
    }

    @Test(expected = RestException.class)
    public void overSizedImage() {
        productDTO.lessThanMaximumAllowedSize(jpgImage, 1L);
    }

    @Test
    public void isValidDimension() {
        assertThat(productDTO.isValidDimension(jpgImage, 2048, 2048)).isTrue();
        assertThat(productDTO.isValidDimension(pngImage)).isTrue();
        assertThat(productDTO.isValidDimension(gifImage)).isTrue();
    }

    @Test(expected = RestException.class)
    public void widthLargerThan_1() {
        productDTO.isValidDimension(pngImage, 1, 1000);
    }

    @Test(expected = RestException.class)
    public void heightLargerThan_1() {
        productDTO.isValidDimension(pngImage, 1000, 1);
    }

}
