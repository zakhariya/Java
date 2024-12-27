package ua.lpr.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.service.SystemService;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Value("${security.token}")
    private String token;

    @GetMapping({"", "/", "**/welcome"})
    public @ResponseBody String welcomePage() {
        return "Welcome!";
    }

    @GetMapping("/os")
    public @ResponseBody String getOSName() {
        return System.getProperty("os.name");
    }

    @PostMapping("shutdown")
    public @ResponseBody ResponseEntity<HttpStatus> systemShutdown(
            @RequestBody(required = false) Parameters parameters, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (parameters != null && ! systemService.shutdown(parameters)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("reboot")
    public @ResponseBody ResponseEntity<HttpStatus> systemReboot(
            @RequestBody(required = false) Parameters parameters, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (parameters != null && ! systemService.reboot(parameters)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

