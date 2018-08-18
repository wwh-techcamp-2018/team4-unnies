package com.baemin.nanumchan.product.domain.cloud;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.baemin.nanumchan.product.exception.FileConvertException;
import com.baemin.nanumchan.product.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class S3Uploader {

    private static final String STATIC = "static";

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) {
        File file = null;
        try {
            file = convert(multipartFile);
        } catch (IOException e) {
            throw new FileConvertException();
        }
        return upload(file);
    }

    private String upload(File file) {
        String uploadImageUrl = putS3(file, STATIC + "/" + file.getName());
        return uploadImageUrl;
    }

    private String putS3(File file, String location) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, location, file).withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucket, location).toString();
        } catch (Exception e) {
            throw new UploadFailedException();
        } finally {
            remove(file);
        }
    }

    private void remove(File file) {
        if (!file.delete()) {
            throw new RuntimeException();
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(LocalDateTime.now().toString() + multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return convertFile;
        }
        throw new FileConvertException();
    }
}
