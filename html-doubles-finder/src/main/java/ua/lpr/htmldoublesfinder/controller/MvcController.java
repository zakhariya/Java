package ua.lpr.htmldoublesfinder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MvcController {

    @Value("${var.url.get.email.list}")
    private String urlGetEmailList;

    @GetMapping({"", "/", "**/welcome"})
    public String mainPage(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);

        return "index";
    }

    @GetMapping({"/emails", "/emails/"})
    public String emailsPage(Model model) {
        model.addAttribute("urlGetEmailList", urlGetEmailList);

        return "emails";
    }

    @GetMapping({"/comparator", "/comparator/"})
    public String comparatorPage() {
        return "comparator";
    }
}
