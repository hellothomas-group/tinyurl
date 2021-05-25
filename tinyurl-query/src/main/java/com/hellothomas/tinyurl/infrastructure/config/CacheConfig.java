package com.hellothomas.tinyurl.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hellothomas.tinyurl.infrastructure.exception.IgnoreExceptionCacheErrorHandler;
import com.hellothomas.tinyurl.infrastructure.property.CaffeineProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.hellothomas.tinyurl.common.constants.Constants.CAFFEINE_NULL_CACHE_NAME;

/**
 * @className CacheConfig
 * @author Thomas
 * @date 2020/11/29 22:49
 * @description
 * @version 1.0
 */
@Slf4j
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    private final RedisConnectionFactory redisConnectionFactory;
    private final CaffeineProperty caffeineProperty;

    public CacheConfig(RedisConnectionFactory redisConnectionFactory, CaffeineProperty caffeineProperty) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.caffeineProperty = caffeineProperty;
    }

    @Override
    public CacheManager cacheManager() {
        // 使用jackson2JsonRedisSerializer来序列化反序列化redis的值、默认使用的是jdk序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();

        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Java8日期时间类支持
        om.findAndRegisterModules();

        // 指定要序列化的域 ANY是都有
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入类型必须是非final的
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        log.info("caffeineProperty: {}", caffeineProperty);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(caffeineProperty.getInitialCapacity())
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight(最大权重)不可以同时使用，
                .maximumSize(caffeineProperty.getMaximumSize())
                //最后一次写入多久过期
                .expireAfterWrite(caffeineProperty.getExpireAfterWrite(), TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        //根据名字可以创建多个cache，但是多个cache使用相同的策略
        cacheManager.setCacheNames(Arrays.asList(CAFFEINE_NULL_CACHE_NAME));
        return cacheManager;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }
}
