package ua.lpr.notificationservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/system")
public class SystemController {

    @Value("${security.token}")
    private String token;

    @GetMapping("/")
    public String test() {
        return "Test";
    }
}
