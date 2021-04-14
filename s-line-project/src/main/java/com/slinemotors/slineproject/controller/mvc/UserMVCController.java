package com.slinemotors.slineproject.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserMVCController {

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("title", "user panel | Main");

        return "user/index";
    }
}
