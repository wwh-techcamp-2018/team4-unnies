package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.dto.*;
import com.baemin.nanumchan.exception.UnAuthenticationException;
import com.baemin.nanumchan.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserService {

    private static final Double ZERO = 0.0;

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

    @Autowired
    private ImageStorage imageStorage;

    public User signUp(SignUpDTO signUpDTO) {
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

    public UserDetailDTO getUserInfo(User loginUser, Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        boolean isMine = true;

        if (loginUser == null || !user.equals(loginUser))
            isMine = false;

        return UserDetailDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .aboutMe(user.getAboutMe())
                .imageUrl(user.getImageUrl())
                .createdProductsCount(productRepository.countByOwnerId(id))
                .receivedProductsCount(orderRepository.countByParticipantId(id))
                .createdReviewsCount(reviewRepository.countByWriterId(id))
                .receivedReviewsCount(reviewRepository.countByChefId(id))
                .avgRating(reviewRepository.getAvgRatingByWriterId(id).orElse(ZERO))
                .isMine(isMine)
                .build();
    }

    public Page<Review> createdReviews(Long writerId, Pageable pageable) {
        return reviewRepository.findAllByWriterIdOrderByIdDesc(writerId, pageable);
    }

    public Page<ProductDetailDTO> createdProducts(Long ownerId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByOwnerIdOrderByIdDesc(ownerId, pageable);
        List<ProductDetailDTO> productDetailDTOS = products.stream().map(product -> {
            int orderCount = orderRepository.countByProductId(product.getId());
            Double ownerRating = reviewRepository.getAvgRatingByWriterId(product.getOwner().getId()).orElse(ZERO);
            return ProductDetailDTO.builder()
                    .product(product)
                    .orderCount(orderCount)
                    .status(product.calculateStatus(orderCount))
                    .ownerRating(ownerRating)
                    .build();
        }).collect(Collectors.toList());

        return new PageImpl<>(productDetailDTOS);
    }

    public UserModifyDTO modifyUserInfo(User loginUser, UserModifyDTO userModifyDTO, HttpSession session) {
        User user = userRepository.findById(userModifyDTO.getId()).orElseThrow(EntityNotFoundException::new);
        if (!user.equals(loginUser)) throw UnAuthenticationException.invalidUser();

        user.setAboutMe(userModifyDTO.getAboutMe());

        if (userModifyDTO.getFile() != null) {
            user.setImageUrl(imageStorage.upload(userModifyDTO.getFile()));
        }

        User modifiedUser = userRepository.save(user);
        SessionUtils.setUserInSession(session, modifiedUser);

        return UserModifyDTO.builder()
                .id(modifiedUser.getId())
                .aboutMe(modifiedUser.getAboutMe())
                .imageUrl(modifiedUser.getImageUrl())
                .build();
    }
}