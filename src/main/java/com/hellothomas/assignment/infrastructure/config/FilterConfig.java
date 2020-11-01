package com.hellothomas.assignment.infrastructure.config;

import com.hellothomas.assignment.infrastructure.filter.ReverseProxyFilter;
import com.hellothomas.assignment.infrastructure.http.HttpRequestDataExtractor;
import com.hellothomas.assignment.infrastructure.http.RequestForwarder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import static com.hellothomas.assignment.constants.Constants.PROXY_PATH;

/**
 * @className FilterConfig
 * @author Thomas
 * @date 2020/10/30 22:55
 * @description
 * @version 1.0
 */
@Configuration
@Slf4j
public class FilterConfig {
    @Bean
    @ConditionalOnMissingBean
    public ReverseProxyFilter reverseProxyFilter(HttpRequestDataExtractor httpRequestDataExtractor,
                                                 RequestForwarder requestForwarder) {
        return new ReverseProxyFilter(httpRequestDataExtractor, requestForwarder);
    }

    @Bean
    public FilterRegistrationBean<ReverseProxyFilter> registerReverseProxyFilter(ReverseProxyFilter reverseProxyFilter) {
        FilterRegistrationBean<ReverseProxyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(reverseProxyFilter);
        filterRegistrationBean.addUrlPatterns(PROXY_PATH + "/*");
        filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        log.info("reverseProxyFilter urls:{}", filterRegistrationBean.getUrlPatterns());
        return filterRegistrationBean;
    }
}
