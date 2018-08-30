package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.exception.UnAuthenticationException;
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
public class SignUpDTO {

    @NotEmpty
    @Pattern(regexp = ValidateRegex.EMAIL)
    private String email;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.USERNAME)
    private String name;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PASSWORD)
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PHONE)
    private String phoneNumber;

    @NotEmpty
    private String address;

    @NotEmpty
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
