package com.hellothomas.assignment.enums;

public enum ErrorCodeEnum implements ICodeEnum {
    URL_FORMAT_ERROR("CODE001", "URL格式错误"),
    FORWARD_ERROR("CODE002", "请求转发异常,异常为:{0}"),
    EXTRACT_BODY_ERROR("CODE003", "解析body异常,异常为:{0}"),
    GENERATE_URI_ERROR("CODE004", "重写URI异常,uri为:{0}"),
    RESPONSE_PROCESS_ERROR("CODE005", "响应体处理失败,异常为:{0}"),
    URL_NOT_EXIST("CODE006", "URL不存在"),
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
