package com.hellothomas.assignment.infrastructure.http;

import com.hellothomas.assignment.exception.MyException;
import com.hellothomas.assignment.service.UniqueSeqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static com.hellothomas.assignment.constants.Constants.EMPTY_STRING;
import static com.hellothomas.assignment.constants.enums.ErrorCodeEnum.EXTRACT_BODY_ERROR;
import static com.hellothomas.assignment.constants.enums.ErrorCodeEnum.GENERATE_URI_ERROR;
import static com.hellothomas.assignment.constants.enums.ProtocolEnum.HTTP;
import static com.hellothomas.assignment.constants.enums.ProtocolEnum.HTTPS;

@Slf4j
@Component
public class HttpRequestDataExtractor {

    private final UniqueSeqService uniqueSeqService;

    public HttpRequestDataExtractor(UniqueSeqService uniqueSeqService) {
        this.uniqueSeqService = uniqueSeqService;
    }

    public byte[] extractBody(HttpServletRequest request) {
        try {
            return IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new MyException(EXTRACT_BODY_ERROR, e);
        }
    }

    public HttpHeaders extractHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            List<String> value = Collections.list(request.getHeaders(name));
            headers.put(name, value);
        }
        return headers;
    }

    public HttpMethod extractHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod());
    }

    public String extractUri(HttpServletRequest request) {
        return request.getRequestURI() + getQuery(request);
    }

    public String extractHost(HttpServletRequest request) {
        return request.getServerName();
    }

    private String getQuery(HttpServletRequest request) {
        return request.getQueryString() == null ? EMPTY_STRING : "?" + request.getQueryString();
    }

    public URI getUri(HttpServletRequest request, String prefixPath) {
        String seqEncode = extractUriWithout(request, prefixPath + "/");
        String originUrlStr = uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
        return generateURI(originUrlStr);
    }

    public String extractUriWithout(HttpServletRequest request, String remove) {
        String uri = this.extractUri(request);
        return StringUtils.removeStartIgnoreCase(uri, remove);
    }

    private String replaceProtocol(String uri) {
        if (uri.startsWith(HTTPS.getValue())) {
            return uri.replace(HTTPS.getValue(), HTTP.getValue());
        }
        return uri;
    }

    private URI generateURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new MyException(GENERATE_URI_ERROR, uri);
        }
    }
}
