package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.utils.ValidateRegex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotEmpty
    @Pattern(regexp = ValidateRegex.EMAIL)
    private String email;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PASSWORD)
    private String password;

    public boolean matchPassword(PasswordEncoder passwordEncoder, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}