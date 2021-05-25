package xyz.hellothomas.tinyurl.generator.common.enums;

import xyz.hellothomas.tinyurl.common.common.enums.ICodeEnum;

public enum GeneratorErrorCodeEnum implements ICodeEnum {
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

    GeneratorErrorCodeEnum(String code, String message) {
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
