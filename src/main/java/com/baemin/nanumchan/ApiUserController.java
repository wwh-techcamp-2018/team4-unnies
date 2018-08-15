package com.baemin.nanumchan;

import com.baemin.nanumchan.domain.SignUpDTO;
import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.service.UserService;
import com.baemin.nanumchan.utils.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping
    public ResponseEntity<RestResponse> create(@Valid @RequestBody SignUpDTO signUpDTO){
         User user = userService.save(signUpDTO);
         return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }
}
