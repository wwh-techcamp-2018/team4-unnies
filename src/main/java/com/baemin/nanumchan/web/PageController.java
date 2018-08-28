package com.baemin.nanumchan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id) {
        return "/detail";
    }
}
