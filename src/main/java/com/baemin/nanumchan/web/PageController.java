package com.baemin.nanumchan.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class PageController {
    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model){
      model.addAttribute("productId",id);
      return "/detail";
    }
}
