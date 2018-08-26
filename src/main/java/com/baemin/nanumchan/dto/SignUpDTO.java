package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import com.baemin.nanumchan.utils.ValidateRegex;
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
    @Pattern(regexp = ValidateRegex.EMAIL)
    private String email;

    @NotNull
    @Pattern(regexp = ValidateRegex.USERNAME)
    private String name;

    @NotNull
    @Pattern(regexp = ValidateRegex.PASSWORD)
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Pattern(regexp = ValidateRegex.PHONE)
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String addressDetail;

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
                .addressDetail(addressDetail)
                .build();
    }
}
