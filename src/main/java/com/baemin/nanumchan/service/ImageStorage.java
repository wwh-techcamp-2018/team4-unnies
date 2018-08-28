package com.baemin.nanumchan.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {

    public String upload(MultipartFile file);

}
