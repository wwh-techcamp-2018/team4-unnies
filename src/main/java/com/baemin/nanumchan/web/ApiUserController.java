package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.dto.LoginDTO;
import com.baemin.nanumchan.dto.SignUpDTO;
import com.baemin.nanumchan.security.LoginUser;
import com.baemin.nanumchan.service.UserService;
import com.baemin.nanumchan.utils.RestResponse;
import com.baemin.nanumchan.utils.SessionUtils;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{id}/mypage")
    public ResponseEntity<RestResponse> getInfo(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(userService.getUserInfo(id)));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<RestResponse> getMyReviews(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(userService.getMyReviews(id, pageable)));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<RestResponse> getMyProducts(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(userService.getMyProducts(id, pageable)));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser User user, HttpSession session) {
        SessionUtils.removeUserInSession(session);
        return ResponseEntity.ok().build();
    }
}
