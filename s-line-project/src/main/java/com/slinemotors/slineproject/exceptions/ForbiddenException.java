package com.slinemotors.slineproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException implements AccessDeniedHandler {

    private String message;

    public ForbiddenException(String message) {

        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void printStackTrace(PrintWriter s) {

    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.sendError(403);

        e.setStackTrace(new StackTraceElement[0]);
    }
}
