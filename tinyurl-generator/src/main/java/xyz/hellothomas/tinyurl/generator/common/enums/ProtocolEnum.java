package xyz.hellothomas.tinyurl.generator.common.enums;

public enum ProtocolEnum {
    HTTP("http"),
    HTTPS("https"),
    FTP("ftp"),
    GOPHER("gopher"),
    SOCKS("socks"),
    JAR("jar"),
    JAVASCRIPT("javascript"),
    RMI("rmi");

    private String value;

    ProtocolEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
