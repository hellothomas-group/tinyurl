package com.hellothomas.assignment.infrastructure.http;

import com.hellothomas.assignment.infrastructure.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static com.hellothomas.assignment.constants.enums.ErrorCodeEnum.FORWARD_ERROR;
import static com.hellothomas.assignment.constants.enums.ProtocolEnum.HTTPS;

@Slf4j
@Component
public class RequestForwarder {

    private final RestTemplate httpRestTemplate;
    private final RestTemplate httpsRestTemplate;
    private final HttpRequestDataExtractor httpRequestDataExtractor;

    public RequestForwarder(@Qualifier("myHttpRestTemplate") RestTemplate httpRestTemplate,
                            @Qualifier("myHttpsRestTemplate") RestTemplate httpsRestTemplate,
                            HttpRequestDataExtractor httpRequestDataExtractor) {
        this.httpRestTemplate = httpRestTemplate;
        this.httpsRestTemplate = httpsRestTemplate;
        this.httpRequestDataExtractor = httpRequestDataExtractor;
    }

    public ResponseEntity<byte[]> forwardRequest(HttpServletRequest request, URI uri) {
        ResponseEntity<byte[]> response;

        RequestEntity requestEntity = new RequestEntity<>(httpRequestDataExtractor.extractBody(request),
                httpRequestDataExtractor.extractHttpHeaders(request),
                httpRequestDataExtractor.extractHttpMethod(request), uri);
        try {
            if (HTTPS.getValue().equalsIgnoreCase(uri.getScheme().toLowerCase())) {
                response = httpsRestTemplate.exchange(requestEntity, byte[].class);
            } else {
                response = httpRestTemplate.exchange(requestEntity, byte[].class);
            }
        } catch (HttpStatusCodeException ex) {
            response =
                    ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders()).body(ex.getResponseBodyAsByteArray());
        } catch (Exception ex) {
            MyException myException = new MyException(FORWARD_ERROR, ex.getMessage());
            log.error(myException.getMessage());
            throw myException;
        }

        return response;
    }
}
