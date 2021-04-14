package ua.lpr.printservice.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lpr.printservice.model.PrintParams;
import ua.lpr.printservice.service.PrintService;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/print")
public class PrintController {

    @Autowired
    private PrintService printService;

    @Value("${security.token}")
    private String token;

    @PostMapping("/html")
    public ResponseEntity printHtml(
            @RequestBody PrintParams printParams, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String log = "Was printed HTML document on " + printParams.getPrinterName() + " at " + new Date();

        try {
            printService.printHtml(printParams);

            System.out.println(log);
        } catch (DocumentException | PrinterException | IOException ex) {
            ex.printStackTrace();

            return new ResponseEntity(ex.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity(log, HttpStatus.OK);
    }

    @PostMapping("/pdf")
    public ResponseEntity printPdf(
            @RequestBody PrintParams printParams, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String log = "Was printed PDF document on " + printParams.getPrinterName() + " at " + new Date();

        try {
            printService.printPdf(printParams);

            System.out.println(log);
        } catch (PrinterException | IOException ex) {
            ex.printStackTrace();

            return new ResponseEntity(ex.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity(log, HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity printImage(
            @RequestBody PrintParams printParams, @RequestHeader("token") String token) {

        if (!this.token.equals(token)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String log = "Was printed IMAGE document on " + printParams.getPrinterName() + " at " + new Date();

        try {
            printService.printImage(printParams);

            System.out.println(log);
        } catch (PrinterException | DocumentException | IOException ex) {
            ex.printStackTrace();

            return new ResponseEntity(ex.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity(log, HttpStatus.OK);
    }
}
