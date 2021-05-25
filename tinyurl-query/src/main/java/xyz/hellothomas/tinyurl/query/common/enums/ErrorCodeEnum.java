package xyz.hellothomas.tinyurl.query.common.enums;

public enum ErrorCodeEnum implements ICodeEnum {
    URL_NOT_EXIST("CODE001", "URL不存在"),
    URL_PATH_INVALID("CODE002", "URL路径不合法"),
    ;

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
