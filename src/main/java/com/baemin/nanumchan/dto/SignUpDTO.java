package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import com.baemin.nanumchan.utils.Regex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotNull
    @Pattern(regexp = Regex.EMAIL)
    private String email;

    @NotNull
    @Pattern(regexp = Regex.USERNAME)
    private String name;

    @NotNull
    @Pattern(regexp = Regex.PASSWORD)
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Pattern(regexp = Regex.PHONE)
    private String phoneNumber;

    @NotNull
    private String address;

    private boolean matchPassword() {
        return password.equals(confirmPassword);
    }

    public User toEntity(PasswordEncoder passwordEncoder) throws UnAuthenticationException {
        if (!matchPassword()) {
            throw UnAuthenticationException.invalidPassword();
        }

        return User.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
