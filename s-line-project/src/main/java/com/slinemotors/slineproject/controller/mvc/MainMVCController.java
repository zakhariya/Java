package com.slinemotors.slineproject.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainMVCController {

    @GetMapping({"", "/"})
    public String mainPage(Model model) {
        model.addAttribute("title", "Главная");

        return "index";
    }

    //TODO: remove
    @GetMapping({"test", "**/test"})
    public String testPage(
            Model model,
            @RequestParam(value="name", required=false, defaultValue="World") String name,
            @RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
            if (key.equals("authorization")) {
                model.addAttribute(key, value);
            }
        });

        return "test";
    }

    @GetMapping("login")
    public String login() {

        return "redirect:/";
    }
}
