package xyz.hellothomas.tinyurl.query.infrastructure.exception;

import xyz.hellothomas.tinyurl.query.common.enums.ICodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.MessageFormat;

/**
 * @ClassName MyException
 * @Author 80234613
 * @Date 2019-7-7 15:22
 * @Descripton
 * @Version 1.0
 */
@Setter
@Getter
@ToString
public class MyException extends RuntimeException {
    private final String code;

    public MyException(ICodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

    public MyException(ICodeEnum codeEnum, Object... params) {
        super(MessageFormat.format(codeEnum.getMessage(), params));
        this.code = codeEnum.getCode();
    }
}
