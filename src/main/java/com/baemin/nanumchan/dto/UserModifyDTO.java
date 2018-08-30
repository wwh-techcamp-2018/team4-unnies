package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.validate.Image;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

import static com.baemin.nanumchan.validate.Image.AcceptType.JPG;
import static com.baemin.nanumchan.validate.Image.AcceptType.PNG;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyDTO {

    private Long id;

    @Nullable
    @Size(max = 255)
    private String aboutMe;

    @Nullable
    @Image(accept = {JPG, PNG}, size = 1_000_000, width = 640, height = 640)
    private MultipartFile file;

    @Nullable
    private String imageUrl;
}

