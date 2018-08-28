package com.baemin.nanumchan.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.baemin.nanumchan.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Profile("production")
@Service
public class S3UploaderService implements ImageStorage {

    private static final String PATH = "img";

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            final String location = String.join("/", PATH, LocalDateTime.now().toString() + multipartFile.getOriginalFilename());
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, location, multipartFile.getInputStream(), new ObjectMetadata())
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            return amazonS3Client.getUrl(bucket, location).toString();
        } catch (Exception e) {
            throw new RestException("파일 업로드 실패");
        }
    }

}
