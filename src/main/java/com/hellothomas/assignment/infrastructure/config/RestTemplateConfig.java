package com.hellothomas.assignment.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
@ConditionalOnClass(RestTemplate.class)
@Slf4j
public class RestTemplateConfig {

    @Bean
    @ConditionalOnMissingBean(name = "myHttpRestTemplate")
    public RestTemplate myHttpRestTemplate(RestTemplateProperties restTemplateProperties) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(restTemplateProperties.getConnectionRequestTimeout());
        httpRequestFactory.setConnectTimeout(restTemplateProperties.getConnectTimeout());
        httpRequestFactory.setReadTimeout(restTemplateProperties.getReadTimeout());
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);

        restTemplate.getMessageConverters().stream().filter(StringHttpMessageConverter.class::isInstance)
                .map(StringHttpMessageConverter.class::cast).forEach(
                a -> {
                    a.setWriteAcceptCharset(false);
                    a.setDefaultCharset(StandardCharsets.UTF_8);
                }
        );

        log.info("myRestTemplate加载成功,配置为{}", restTemplateProperties.toString());
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(name = "myHttpsRestTemplate")
    public RestTemplate myHttpsRestTemplate(RestTemplateProperties restTemplateProperties) {
        HttpsClientRequestFactory httpsClientRequestFactory = new HttpsClientRequestFactory();
        httpsClientRequestFactory.setConnectTimeout(restTemplateProperties.getConnectTimeout());
        httpsClientRequestFactory.setReadTimeout(restTemplateProperties.getReadTimeout());
        RestTemplate restTemplate = new RestTemplate(httpsClientRequestFactory);
        return restTemplate;
    }
}
