package xyz.hellothomas.tinyurl.generator.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2
 * @Author 80234613
 * @Date 2019-7-8 8:47
 * @Descripton Swagger2配置
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("xyz.hellothomas.tinyurl"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("长链接转短链接Swagger2文档")
                .description("API接口文档")
                .termsOfServiceUrl("https://www.hellothomas.com")
                .contact(new Contact("Thomas",
                        "http://www.hellothomas.com",
                        "tyty2017@cmbchina.com"))
                .version("1.0.1")
                .build();
    }
}
