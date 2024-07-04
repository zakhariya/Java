package ua.lpr.notificationservice.controller;

import org.springframework.web.bind.annotation.*;
import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Value("${security.token}")
    private String token;

    @GetMapping("/")
    public @ResponseBody String welcome() {

        return "Welcome! This is notification service.";
    }

    @PostMapping("/all")
    public ResponseEntity notifyEmployeesByAll(
            @RequestBody Parameters parameters, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        notificationService.notifyByAll(parameters);

        return new ResponseEntity(HttpStatus.OK);
    }
}
