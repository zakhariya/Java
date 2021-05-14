package ua.lpr.webhelper.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TrataException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { HttpClientErrorException.class })
    protected void handleConflict(RuntimeException ex, HttpServletRequest httpRequest) {
        String bodyOfResponse = "This should be application specific";

        System.out.println("___________________________");
        System.out.println(httpRequest.getRequestURI());
        System.out.println("___________________________");
    }

    //    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
//    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "This should be application specific";
//
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
}
