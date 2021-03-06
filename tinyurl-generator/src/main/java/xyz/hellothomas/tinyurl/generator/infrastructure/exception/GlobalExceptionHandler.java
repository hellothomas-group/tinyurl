package xyz.hellothomas.tinyurl.generator.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hellothomas.tinyurl.common.common.enums.ErrorCodeEnum;
import xyz.hellothomas.tinyurl.common.dto.ApiResponse;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * default error 处理
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("default error handler", e);
        return ApiResponse.fail(String.format("%s:%s", ErrorCodeEnum.SYSTEM_ERROR.getMessage(), e.getMessage()),
                ErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ApiResponse myExceptionHandler(MyException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.fail(e.getMessage(), e.getCode());
    }

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class})
    @ResponseBody
    public ApiResponse paramsHandler(Exception e) {
        log.error("传参错误", e);
        return ApiResponse.fail(e.getMessage(), ErrorCodeEnum.PARAM_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse handleException(HttpServletRequest request,
                                       MethodArgumentNotValidException exception) {
        String message = String.join(",", exception.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .collect(Collectors.toList()));
        log.error("handleError: " + message);
        log.error("handException: ", exception);
        return ApiResponse.fail(message, ErrorCodeEnum.PARAM_ERROR.getCode());
    }
}
