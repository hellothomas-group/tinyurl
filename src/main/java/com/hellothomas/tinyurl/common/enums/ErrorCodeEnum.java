package com.hellothomas.tinyurl.common.enums;

public enum ErrorCodeEnum implements ICodeEnum {
    URL_FORMAT_ERROR("CODE001", "URL格式错误"),
    RESPONSE_PROCESS_ERROR("CODE002", "响应体处理失败,异常为:{0}"),
    URL_NOT_EXIST("CODE003", "URL不存在"),
    GET_LOCAL_HOST_ERROR("CODE004", "获取本机host异常,异常为{0}"),
    THREAD_SLEEP_ERROR("CODE005", "线程休眠异常,异常为{0}"),
    GENERATE_SEQ_ERROR("CODE006", "生成序号失败"),
    REFRESH_EXIT_ERROR("CODE007", "刷新重试超限退出"),
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
