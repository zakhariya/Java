package ua.lpr.webhelper.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.entity.URLType;
import ua.lpr.webhelper.service.LinkService;

import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/get")
    public ResponseEntity<Set<Link>> get(
                    @RequestParam String url,
                    @RequestParam URLType type) {

        try {
            return new ResponseEntity<>(linkService.get(url, type), HttpStatus.OK);
        } catch (URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data", e);
        }
    }
}
