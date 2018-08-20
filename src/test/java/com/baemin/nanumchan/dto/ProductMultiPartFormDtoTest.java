package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.dto.ProductMultiPartFormDto;
import com.baemin.nanumchan.exception.ExceedMaximumAllowedFileSizeException;
import com.baemin.nanumchan.exception.InvalidImageDimensionException;
import com.baemin.nanumchan.exception.UnsupportMimeTypeException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMultiPartFormDtoTest {

    private static MultipartFile gifImage;
    private static MultipartFile jpgImage;
    private static MultipartFile pngImage;

    private ProductMultiPartFormDto productMultiPartFormDto;

    @BeforeClass
    public static void loadFiles() throws Exception {
        gifImage = new MockMultipartFile("nyan.gif", "nyan", "image/gif", new FileInputStream(new File("src/test/resources/static/images/nyan.gif")));
        jpgImage = new MockMultipartFile("dog.jpg", "dog", "image/jpg", new FileInputStream(new File("src/test/resources/static/images/dog.jpg")));
        pngImage = new MockMultipartFile("sample.png", "sample", "image/png", new FileInputStream(new File("src/test/resources/static/images/sample.png")));
    }

    @Before
    public void setUp() throws Exception {
        productMultiPartFormDto = new ProductMultiPartFormDto();
    }

    @Test
    public void isJPGImage() {
        assertThat(productMultiPartFormDto.isMimeTypeImage(jpgImage)).isTrue();
    }

    @Test
    public void isPNGImage() {
        assertThat(productMultiPartFormDto.isMimeTypeImage(pngImage)).isTrue();
    }

    @Test(expected = UnsupportMimeTypeException.class)
    public void notSupportGIFImage() {
        productMultiPartFormDto.isMimeTypeImage(gifImage);
    }

    @Test
    public void lessThanMaximumAllowedSize() {
        assertThat(productMultiPartFormDto.lessThanMaximumAllowedSize(jpgImage)).isTrue();
        assertThat(productMultiPartFormDto.lessThanMaximumAllowedSize(pngImage)).isTrue();
        assertThat(productMultiPartFormDto.lessThanMaximumAllowedSize(gifImage)).isTrue();
    }

    @Test(expected = ExceedMaximumAllowedFileSizeException.class)
    public void overSizedImage() {
        productMultiPartFormDto.lessThanMaximumAllowedSize(jpgImage, 1L);
    }

    @Test
    public void isValidDimension() {
        assertThat(productMultiPartFormDto.isValidDimension(jpgImage, 2048, 2048)).isTrue();
        assertThat(productMultiPartFormDto.isValidDimension(pngImage)).isTrue();
        assertThat(productMultiPartFormDto.isValidDimension(gifImage)).isTrue();
    }

    @Test(expected = InvalidImageDimensionException.class)
    public void widthLargerThan_1() {
        productMultiPartFormDto.isValidDimension(pngImage, 1, 1000);
    }

    @Test(expected = InvalidImageDimensionException.class)
    public void heightLargerThan_1() {
        productMultiPartFormDto.isValidDimension(pngImage, 1000, 1);
    }

}