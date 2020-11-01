package com.hellothomas.assignment.infrastructure.filter;

import com.hellothomas.assignment.exception.MyException;
import com.hellothomas.assignment.infrastructure.http.HttpRequestDataExtractor;
import com.hellothomas.assignment.infrastructure.http.RequestForwarder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

import static com.hellothomas.assignment.constants.Constants.PROXY_PATH;
import static com.hellothomas.assignment.enums.ErrorCodeEnum.RESPONSE_PROCESS_ERROR;

@Slf4j
public class ReverseProxyFilter extends OncePerRequestFilter {
    protected final HttpRequestDataExtractor httpRequestDataExtractor;
    protected final RequestForwarder requestForwarder;

    public ReverseProxyFilter(HttpRequestDataExtractor httpRequestDataExtractor,
                              RequestForwarder requestForwarder) {
        this.httpRequestDataExtractor = httpRequestDataExtractor;
        this.requestForwarder = requestForwarder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String originUri = httpRequestDataExtractor.extractUri(request);
        String originHost = httpRequestDataExtractor.extractHost(request);

        log.debug("Incoming request, method:{}, host:{}, uri:{}", request.getMethod(), originHost, originUri);

        if (originUri.contains(PROXY_PATH)) {
            URI uri = this.httpRequestDataExtractor.getUri(request, PROXY_PATH);
            response.setStatus(HttpStatus.FOUND.value());
            response.setHeader("Location", uri.toString());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void processResponse(HttpServletResponse response, ResponseEntity<byte[]> responseEntity) {
        response.setStatus(responseEntity.getStatusCodeValue());
        responseEntity.getHeaders().entrySet().stream()
                .filter(e -> !(e.getKey().contains("Transfer-Encoding")))
                .filter(e -> !(e.getKey().contains("Connection")))
                .forEach(e -> e.getValue().forEach(value -> response.addHeader(e.getKey(), value)));

        if (responseEntity.getBody() != null) {
            try {
                response.getOutputStream().write(responseEntity.getBody());
            } catch (IOException e) {
                MyException myException = new MyException(RESPONSE_PROCESS_ERROR, e);
                log.error(myException.getMessage());
                throw myException;
            }
        }
    }

}
