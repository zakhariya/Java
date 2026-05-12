package ua.lpr.util.exceptions;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.lpr.util.Functions;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    //StandardServletMultipartResolver
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException ex,
                               RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        redirectAttributes.addFlashAttribute("url", httpServletRequest.getHeader("referer"));
        redirectAttributes.addFlashAttribute("message", "MultipartException:<br>" + ex.getLocalizedMessage());

        return "error";

    }

    //CommonsMultipartResolver
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(MaxUploadSizeExceededException ex,
                               RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        redirectAttributes.addFlashAttribute("url", httpServletRequest.getHeader("referer"));
        redirectAttributes.addFlashAttribute("message", "MaxUploadSizeExceededException:<br>" + ex.getLocalizedMessage());

        return "error";
    }

    // 405 - Request method not supported
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleError3(HttpRequestMethodNotSupportedException ex,
                               RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest){

        String url = Functions.getUrlByDeep(httpServletRequest.getHeader("referer"), 2);

        redirectAttributes.addFlashAttribute("url", url);
        redirectAttributes.addFlashAttribute("message",
                "Сессия завершена. Необходима повторная авторизация.<br>" +
                        "<br>HttpRequestMethodNotSupportedException:<br>" + ex.getLocalizedMessage());

        return "redirect:/error";
    }

    // database
    @ExceptionHandler(TransientDataAccessResourceException.class)
    public ModelAndView handlerError4(TransientDataAccessResourceException ex,
                                      HttpServletRequest request){

        String url = request.getHeader("referer");
        String message = "";

        if(ex.getLocalizedMessage().contains("Packet for query is too large")
                | ex.getLocalizedMessage().contains("Недопустимая длина")) {

            message = "Размер данных превышает возможный для записи в базу данных.";
        }

        return Functions.errorPage(message, url);
    }

    //sometimes see error on console
    @ExceptionHandler(IllegalArgumentException.class)
    public void handlerError5(IllegalArgumentException ex, HttpServletRequest request){
        String msg = ex.getMessage();
        if(msg.indexOf("HTTP method names must be tokens") > -1)
            System.out.println("IP - " + Functions.getClientIp(request));

        System.err.println("Error from cached exception" + msg);
    }

    //test
//    @ExceptionHandler(BadCredentialsException.class)
//    public void handlerError6(BadCredentialsException ex, HttpServletRequest request){
//        System.out.println("Bad creds. IP - " + Functions.getClientIp(request));
//    }
}
