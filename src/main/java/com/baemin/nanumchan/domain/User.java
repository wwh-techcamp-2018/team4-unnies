package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.baemin.nanumchan.utils.ValidateRegex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User extends AbstractEntity {

    public static final GuestUser GUEST_USER = new GuestUser();

    @NotNull
    @Pattern(regexp = ValidateRegex.EMAIL)
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Pattern(regexp = ValidateRegex.USERNAME)
    @Column(nullable = false, updatable = false)
    private String name;

    @NotNull
    @Pattern(regexp = ValidateRegex.PHONE)
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String address;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String addressDetail;

    @Nullable
    @Length(max = 255)
    private String aboutMe;

    @Nullable
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

    public boolean equalsEmail(User user){
        if(email.equals(user.getEmail())){
            return true;
        }
        return false;
    }
}
