package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.dto.SignUpDTO;
import com.baemin.nanumchan.dto.UserModifyDTO;
import com.baemin.nanumchan.security.LoginUser;
import com.baemin.nanumchan.service.UserService;
import com.baemin.nanumchan.utils.RestResponse;
import com.baemin.nanumchan.utils.SessionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping
    public ResponseEntity<RestResponse> create(@Valid @RequestBody SignUpDTO signUpDTO) {
        User user = userService.signUp(signUpDTO);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpSession session) {
        User user = userService.login(loginDTO);
        SessionUtils.setUserInSession(session, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> getInfo(@LoginUser(required = false) User user, @PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(userService.getUserInfo(user, id)));
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestResponse> modify(@LoginUser User user, @Valid UserModifyDTO userModifyDTO, HttpSession session) {
        return ResponseEntity.ok(RestResponse.success(userService.modifyUserInfo(user, userModifyDTO, session)));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<RestResponse> createdReviews(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(userService.createdReviews(id, pageable)));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<RestResponse> createdProducts(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(userService.createdProducts(id, pageable)));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser User user, HttpSession session) {
        SessionUtils.removeUserInSession(session);
        return ResponseEntity.ok().build();
    }
}
