package ua.lpr.htmldoublesfinder.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lpr.htmldoublesfinder.entity.Result;
import ua.lpr.htmldoublesfinder.service.AccessService;
import ua.lpr.htmldoublesfinder.service.SiteService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/emails")
public class EmailsController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private AccessService accessService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/get-list")
    public ResponseEntity<Result> getEmails(@RequestBody Set<String> urls) {

        if (!accessService.clientHasAccess(urls, request)) {
            return new ResponseEntity("Доступ к использованию сервиса запрещен.", HttpStatus.FORBIDDEN);
        }

        Result result = null;

        try {
            result = siteService.getEmailsListFromPages(urls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
