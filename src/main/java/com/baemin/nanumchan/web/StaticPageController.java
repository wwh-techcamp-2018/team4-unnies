package com.baemin.nanumchan.web;

import com.baemin.nanumchan.domain.User;
import com.baemin.nanumchan.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    @GetMapping("/upload")
    public String upload(@LoginUser(required = false) User user) {
        if (user.isGuestUser()) {
            return "redirect:/login.html";
        }
        return "/upload";
    }

}
