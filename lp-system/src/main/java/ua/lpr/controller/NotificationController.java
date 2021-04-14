package ua.lpr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @PostMapping("")
    public ResponseEntity test() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
