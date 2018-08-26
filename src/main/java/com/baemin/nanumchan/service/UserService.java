package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.dto.SignUpDTO;
import com.baemin.nanumchan.dto.UserDetailDTO;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public User save(SignUpDTO signUpDTO) {
        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw UnAuthenticationException.existEmail();
        }
        return userRepository.save(signUpDTO.toEntity(passwordEncoder));
    }

    public User login(LoginDTO loginDTO) {
        User maybeUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(UnAuthenticationException::invalidEmail);

        if (!loginDTO.matchPassword(passwordEncoder, maybeUser)) {
            throw UnAuthenticationException.invalidPassword();
        }

        return maybeUser;
    }

    public UserDetailDTO getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return UserDetailDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .aboutMe(user.getAboutMe())
                .imageUrl(user.getImageUrl())
                .orderToCount(orderRepository.countByParticipantId(id))
                .orderFromCount(productRepository.countByOwnerId(id))
                .reviewToCount(reviewRepository.countByWriterId(id))
                .reviewFromCount(reviewRepository.countByChefId(id))
                .build();
    }

    public Page<Review> getMyReviews(Long writerId, Pageable pageable) {
        return reviewRepository.findAllByWriterIdOrderByIdDesc(writerId, pageable);
    }

    public Page<Product> getMyProducts(Long ownerId, Pageable pageable) {
        return productRepository.findAllByOwnerIdOrderByIdDesc(ownerId, pageable);
    }
}