package com.baemin.nanumchan.service;

import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
@Profile("development")
@Service
public class LocalFileUploaderService implements ImageStorage {

    @Value("${user.home}")
    private String home;

    @Value("${local.storage.path}")
    private String relativePath;

    private String absolutePath;

    @PostConstruct
    public void initPath() throws IOException {
        Path absolutePath = Paths.get(home + relativePath);
        log.debug("### {}",absolutePath);
        if (!Files.exists(absolutePath)) {
            Files.createDirectories(absolutePath);
        }
        this.absolutePath = absolutePath.toString();
    }

    public String upload(MultipartFile multipartFile) {
        try {
            File file = convert(multipartFile);
            return asResource(file.getName()).getURI().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        final String filePath = getFilePath(multipartFile.getOriginalFilename());
        File file = new File(filePath);
        if (file.createNewFile()) {
            copyFileStream(file, multipartFile.getInputStream());
        }
        return file;
    }

    private void copyFileStream(File target, InputStream input) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(target)) {
            IOUtils.copy(input, fos);
        } catch (IOException e) {
            Files.deleteIfExists(Paths.get(target.getAbsolutePath()));
        }
    }

    private String getFilePath(String fileName) {
        return String.join("/", home, relativePath, LocalDateTime.now().toString() + fileName);
    }

    public Resource asResource(String fileName) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(this.absolutePath).resolve(fileName);
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return resource;
    }

}
