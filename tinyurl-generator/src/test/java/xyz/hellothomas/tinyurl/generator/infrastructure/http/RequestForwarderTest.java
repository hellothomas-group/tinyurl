package xyz.hellothomas.tinyurl.generator.infrastructure.http;

import xyz.hellothomas.tinyurl.generator.infrastructure.exception.MyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;

import static xyz.hellothomas.tinyurl.generator.common.enums.ErrorCodeEnum.URL_NOT_EXIST;

@RunWith(JUnit4.class)
public class RequestForwarderTest {

    @Test
    public void testURI() {
        URI uri = URI.create("https://localhost:8000/index.html");
        System.out.println(uri.getScheme());
        System.out.println(uri.getSchemeSpecificPart());
        System.out.println(uri.getRawSchemeSpecificPart());
        System.out.println(uri);
    }

    @Test
    public void testMyException() {
        Exception e = new RuntimeException("123456");
        MyException myException = new MyException(URL_NOT_EXIST, e);
        System.out.println(myException.getCode());
        System.out.println(myException.getMessage());
        System.out.println(myException.getMessage());
    }

    @Test
    public void testStringToNum() {
        String str = String.format("%08d", 1);
        System.out.println(str);
        long num = Long.valueOf(str);
        System.out.println(num);
    }

}
