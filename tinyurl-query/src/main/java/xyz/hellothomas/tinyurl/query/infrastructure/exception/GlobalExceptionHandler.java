package xyz.hellothomas.tinyurl.query.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * @className ExceptionHandlerAdvice
 * @author 80234613 唐圆
 * @date 2020/12/29 19:01
 * @descripton
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception exception, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {
        log.info("host: {}, uri: {}, exception: {}", request.getRemoteAddr(), request.getRequestURI(),
                ExceptionUtils.getStackTrace(exception));
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
        return new ModelAndView("error_system.html");
    }

    @ExceptionHandler(value = MyException.class)
    public void myExceptionHandler(MyException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("host: {}, uri: {}, exception: {}", request.getRemoteAddr(), request.getRequestURI(),
                exception.getMessage());
        response.sendError(SC_NOT_FOUND);
    }
}
