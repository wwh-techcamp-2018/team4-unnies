package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.SignUpDTO;
import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.domain.UserRepository;
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

    public User save(SignUpDTO signUpDTO) throws UnAuthenticationException {

        if (!signUpDTO.matchPassword()) {
            throw UnAuthenticationException.invalidPassword();
        }

        return userRepository.save(signUpDTO.toEntity(passwordEncoder));
    }
}