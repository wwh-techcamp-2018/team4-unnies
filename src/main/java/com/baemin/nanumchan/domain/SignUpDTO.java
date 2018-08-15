package com.baemin.nanumchan.domain;

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
    @Pattern(regexp = Regex.EMAIL, message = "Invalid email")
    private String email;

    @NotNull
    @Pattern(regexp = Regex.USERNAME, message = "Invalid name")
    private String name;

    @NotNull
    @Pattern(regexp = Regex.PASSWORD, message = "Invalid password")
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Pattern(regexp = Regex.PHONE, message = "Invalid phoneNumber number")
    private String phoneNumber;

    @NotNull
    private String address;

    public boolean matchPassword() {
        return password.equals(confirmPassword);
    }

    public User toEntity(PasswordEncoder passwordEncoder) {

        return User.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();

    }
}
