package ua.lpr.webhelper.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

@ControllerAdvice
@Slf4j
public class HttpClientException implements ResponseErrorHandler {

    private String url;

    public HttpClientException setUrl(String url) {
        this.url = url;

        return this;
    }

    @ExceptionHandler(value = { UnknownHostException.class })
    public void handleError() {
        log.warn("Error at: " + url);
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        log.warn(url);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        log.warn(url.toString());
    }
}

