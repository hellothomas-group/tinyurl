package xyz.hellothomas.tinyurl.query.common.constants;

/**
 * @author Thomas
 * @date 2020/10/30 20:40
 * @description
 * @version 1.0
 */
public class QueryConstants {
    public static final String ID_ENCODE_CACHE_NAME = "TinyUrl.IdEncode.";
    public static final String CAFFEINE_NULL_CACHE_NAME = "NullTinyUrl";
    private QueryConstants() {
        throw new IllegalStateException("Constants class");
    }
}
