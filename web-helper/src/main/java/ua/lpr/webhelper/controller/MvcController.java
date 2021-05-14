package ua.lpr.webhelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

    @GetMapping({"", "/"})
    public String mainPage() {
        return "index";
    }

    @GetMapping({"/links", "/links/"})
    public String linksPage() {
        return "links";
    }

    @GetMapping({"/emails", "/emails/"})
    public String emailsPage() {
        return "emails";
    }

    @GetMapping({"/comparator", "/comparator/"})
    public String comparatorPage() {
        return "comparator";
    }
}
