package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.baemin.nanumchan.utils.ValidateRegex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends AbstractEntity {

    public static final GuestUser GUEST_USER = new GuestUser();

    @NotEmpty
    @Pattern(regexp = ValidateRegex.EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.USERNAME)
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PHONE)
    @Column(nullable = false)
    private String phoneNumber;

    @NotEmpty
    @Column(nullable = false, length = 50)
    private String address;

    @NotEmpty
    @Column(nullable = false, length = 50)
    private String addressDetail;

    @Lob
    private String aboutMe;

    private String imageUrl;

    @JsonIgnore
    public boolean isGuestUser() {
        return false;
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }
}
