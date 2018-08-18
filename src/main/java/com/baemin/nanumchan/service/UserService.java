package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.domain.UserRepository;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.dto.SignUpDTO;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(SignUpDTO signUpDTO) {
        return userRepository.save(signUpDTO.toEntity(passwordEncoder));
    }

    public User login(LoginDTO loginDTO) {
        User maybeUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(UnAuthenticationException::invalidEmail);

        if (!loginDTO.matchPassword(passwordEncoder, maybeUser)) {
            throw UnAuthenticationException.invalidPassword();
        }

        return maybeUser;
    }
}