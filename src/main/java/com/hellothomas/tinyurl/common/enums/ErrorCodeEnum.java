package com.hellothomas.tinyurl.common.enums;

public enum ErrorCodeEnum implements ICodeEnum {
    /**
     * 操作成功
     */
    SUCCESS("SUC0000", "操作成功"),

    /**
     * 操作失败
     */
    FAIL("CODE001", "操作失败"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("CODE002", "系统异常"),

    /**
     * 参数异常
     */
    PARAM_ERROR("CODE003", "参数异常"),

    URL_NOT_EXIST("CODE004", "URL不存在"),
    GET_LOCAL_HOST_ERROR("CODE005", "获取本机host异常,异常为{0}"),
    THREAD_SLEEP_ERROR("CODE006", "线程休眠异常,异常为{0}"),
    GENERATE_SEQ_ERROR("CODE007", "生成序号失败"),
    REFRESH_EXIT_ERROR("CODE008", "刷新重试超限退出"),
    URL_PATH_INVALID("CODE009", "URL路径不合法"),
    URL_ADDRESS_INVALID("CODE010", "URL地址不合法"),
    URL_FORMAT_ERROR("CODE011", "URL格式错误"),
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
