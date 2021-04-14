package com.slinemotors.slineproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private String message;

    public NotFoundException(String message) {

        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void printStackTrace(PrintWriter s) {

    }
}
