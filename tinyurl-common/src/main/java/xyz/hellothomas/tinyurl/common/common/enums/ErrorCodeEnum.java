package xyz.hellothomas.tinyurl.common.common.enums;

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
