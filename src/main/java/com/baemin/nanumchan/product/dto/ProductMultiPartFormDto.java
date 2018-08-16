package com.baemin.nanumchan.product.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductMultiPartFormDto {

    private MultipartFile[] files;

    public ProductMultiPartFormDto() {

    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

}
