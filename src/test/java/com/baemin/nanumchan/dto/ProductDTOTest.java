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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProductDTOTest {

    private static Validator validator;

    private static MultipartFile gifImage;
    private static MultipartFile jpgImage;
    private static MultipartFile pngImage;

    @BeforeClass
    public static void loadFiles() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        gifImage = new MockMultipartFile("nyan.gif", "nyan", "image/gif", new FileInputStream(new File("src/test/resources/static/images/nyan.gif")));
        jpgImage = new MockMultipartFile("dog.jpg", "dog", "image/jpg", new FileInputStream(new File("src/test/resources/static/images/dog.jpg")));
        pngImage = new MockMultipartFile("sample.png", "sample", "image/png", new FileInputStream(new File("src/test/resources/static/images/sample.png")));
    }

    @Test
    public void files_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "files", null).size()).isEqualTo(0);
    }

    @Test
    public void files_개수초과() {
        assertThat(validator.validateValue(ProductDTO.class, "files", Arrays.asList(pngImage, pngImage, pngImage, pngImage, pngImage)).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "files", Arrays.asList(pngImage, pngImage, pngImage, pngImage, pngImage, pngImage)).size()).isEqualTo(1);
    }

    @Test
    public void files_지원하지않는타입() {
        assertThat(validator.validateValue(ProductDTO.class, "files", Arrays.asList(gifImage)).size()).isEqualTo(1);
    }

    @Test
    public void files_크기초과() {
        assertThat(validator.validateValue(ProductDTO.class, "files", Arrays.asList(jpgImage)).size()).isEqualTo(1);
    }

    @Test
    public void files_용량초과() {
        assertThat(validator.validateValue(ProductDTO.class, "files", Arrays.asList(jpgImage)).size()).isEqualTo(1);
    }

    @Test
    public void categoryId_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "categoryId", null).size()).isEqualTo(1);
    }

    @Test
    public void categoryId_min보다_작음() {
        assertThat(validator.validateValue(ProductDTO.class, "categoryId", 0L).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "categoryId", 1L).size()).isEqualTo(0);
    }

    @Test
    public void categoryId_max보다_큼() {
        assertThat(validator.validateValue(ProductDTO.class, "categoryId", 6L).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "categoryId", 7L).size()).isEqualTo(1);
    }

    @Test
    public void name_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "name", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "name", "").size()).isEqualTo(1);
    }

    @Test
    public void name_너무_긴_음식명() {
        assertThat(validator.validateValue(ProductDTO.class, "name", new String(new char[50]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "name", new String(new char[50 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
    }

    @Test
    public void title_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "title", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "title", "").size()).isEqualTo(1);
    }

    @Test
    public void title_너무_긴_제목() {
        assertThat(validator.validateValue(ProductDTO.class, "title", new String(new char[100]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "title", new String(new char[100 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
    }

    @Test
    public void price_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "price", null).size()).isEqualTo(1);
    }

    @Test
    public void price_음수() {
        assertThat(validator.validateValue(ProductDTO.class, "price", -1).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "price", 0).size()).isEqualTo(0);
    }

    @Test
    public void price_너무_큰_값() {
        assertThat(validator.validateValue(ProductDTO.class, "price", 1000000).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "price", 1000000 + 1).size()).isEqualTo(1);
    }

    @Test
    public void price_100원_단위_아님() {
        assertThat(validator.validateValue(ProductDTO.class, "price", 123).size()).isEqualTo(1);
    }

    @Test
    public void description_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "description", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "description", "").size()).isEqualTo(1);
    }

    @Test
    public void description_너무_긴_설명() {
        assertThat(validator.validateValue(ProductDTO.class, "description", new String(new char[100000]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "description", new String(new char[100000 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
    }

    @Test
    public void address_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "address", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "address", "").size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "addressDetail", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "addressDetail", "").size()).isEqualTo(1);
    }

    @Test
    public void address_너무_긴_주소() {
        assertThat(validator.validateValue(ProductDTO.class, "address", new String(new char[50]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "address", new String(new char[50 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "addressDetail", new String(new char[50]).replace('\0', 'A')).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "addressDetail", new String(new char[50 + 1]).replace('\0', 'A')).size()).isEqualTo(1);
    }

    @Test
    public void latlng_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "latitude", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "longitude", null).size()).isEqualTo(1);
    }

    @Test
    public void latlng_범위_초과() {
        assertThat(validator.validateValue(ProductDTO.class, "latitude", -90.0D).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "latitude", -90.1D).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "latitude", 90.0D).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "latitude", 90.1D).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "longitude", -180.0D).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "longitude", -180.1D).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "longitude", 180.0D).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "longitude", 180.1D).size()).isEqualTo(1);
    }

    @Test
    public void maxParticipant_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "maxParticipant", null).size()).isEqualTo(1);
    }

    @Test
    public void maxParticipant_min보다_작음() {
        assertThat(validator.validateValue(ProductDTO.class, "maxParticipant", 0L).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "maxParticipant", 1L).size()).isEqualTo(0);
    }

    @Test
    public void maxParticipant_max보다_큼() {
        assertThat(validator.validateValue(ProductDTO.class, "maxParticipant", 6L).size()).isEqualTo(0);
        assertThat(validator.validateValue(ProductDTO.class, "maxParticipant", 7L).size()).isEqualTo(1);
    }

    @Test
    public void expireDateTime_shareDateTime_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "expireDateTime", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "shareDateTime", null).size()).isEqualTo(1);
    }

    @Test
    public void shareDateTime_expireDateTime_정상() {
        LocalDateTime now = getRoundedMinutes(LocalDateTime.now());
        int sizeOfEmptyForm = validator.validate(ProductDTO.builder().build()).size();

        assertThat(validator.validate(ProductDTO.builder()
                .expireDateTime(now.plusHours(2))
                .shareDateTime(now.plusHours(2))
                .build()).size()).isEqualTo(sizeOfEmptyForm - 2);
    }

        @Test
    public void shareDateTime_expireDateTime_10분_단위() {
        LocalDateTime now = getRoundedMinutes(LocalDateTime.now());
        int sizeOfEmptyForm = validator.validate(ProductDTO.builder().build()).size();

        assertThat(validator.validate(ProductDTO.builder()
                .expireDateTime(now.plusHours(2).withMinute(1))
                .shareDateTime(now.plusHours(2))
                .build()).size()).isEqualTo(sizeOfEmptyForm - 2 + 1);

        assertThat(validator.validate(ProductDTO.builder()
                .expireDateTime(now.plusHours(2))
                .shareDateTime(now.plusHours(2).withMinute(1))
                .build()).size()).isEqualTo(sizeOfEmptyForm - 2 + 1);
    }

    @Test
    public void shareDateTime_expireDateTime_이후() {
        LocalDateTime now = getRoundedMinutes(LocalDateTime.now());
        int sizeOfEmptyForm = validator.validate(ProductDTO.builder().build()).size();

        assertThat(validator.validate(ProductDTO.builder()
                .expireDateTime(now.plusHours(2))
                .shareDateTime(now.plusHours(2).minusNanos(1))
                .build()).size()).isEqualTo(sizeOfEmptyForm - 2 + 1);
    }

    @Test
    public void shareDateTime_expireDateTime_필요() {
        LocalDateTime now = getRoundedMinutes(LocalDateTime.now());
        int sizeOfEmptyForm = validator.validate(ProductDTO.builder().build()).size();

        assertThat(validator.validate(ProductDTO.builder()
                .shareDateTime(now.plusHours(2).minusNanos(1))
                .build()).size()).isEqualTo(sizeOfEmptyForm - 2 + 1);
    }

    @Test
    public void isBowlNeeded_비어있음() {
        assertThat(validator.validateValue(ProductDTO.class, "isBowlNeeded", null).size()).isEqualTo(1);
        assertThat(validator.validateValue(ProductDTO.class, "isBowlNeeded", true).size()).isEqualTo(0);
    }

    private LocalDateTime getRoundedMinutes(LocalDateTime time) {
        time = time.truncatedTo(ChronoUnit.MINUTES);
        int minute = time.getMinute();
        if (minute % 10 != 0) {
            time = time.withMinute(((minute / 10) + 1) * 10 % 60);
        }
        return time;
    }

}
