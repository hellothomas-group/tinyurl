package com.hellothomas.assignment.infrastructure.http;

import com.hellothomas.assignment.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;

import static com.hellothomas.assignment.enums.ErrorCodeEnum.RESPONSE_PROCESS_ERROR;

@RunWith(JUnit4.class)
@Slf4j
public class RequestForwarderTest {

    @Test
    public void testURI() {
        URI uri = URI.create("https://localhost:8000/index.html");
        System.out.println(uri.getScheme());
        System.out.println(uri.getSchemeSpecificPart());
        System.out.println(uri.getRawSchemeSpecificPart());
    }

    @Test
    public void testMyException() {
        Exception e = new RuntimeException("123456");
        MyException myException = new MyException(RESPONSE_PROCESS_ERROR, e);
        System.out.println(myException.getCode());
        System.out.println(myException.getMessage());
        log.error(myException.getMessage());
    }

}
