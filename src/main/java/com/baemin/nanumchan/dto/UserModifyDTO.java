package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.validate.Image;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

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
    @Image
    private MultipartFile file;

    @Nullable
    private String imageUrl;
}

