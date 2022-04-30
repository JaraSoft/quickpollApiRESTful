package com.jarasoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/v1/*.*"))
                .build()
                .apiInfo(apiInfo("v1"))
                .groupName("v1")
                .useDefaultResponseMessages(false)
                ;
    }

    @Bean
    public Docket apiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/v2/*.*"))
                .build()
                .apiInfo(apiInfo("v2"))
                .groupName("v2")
                .useDefaultResponseMessages(false)
                ;
    }

    @Bean
    public Docket apiV3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/v3/*.*"))
                .build()
                .apiInfo(apiInfo("v3"))
                .groupName("v3")
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo apiInfo(String version){
        return new ApiInfo(
                "Quickpoll REST Api",
                "Quickpoll Api for creating and managing polls",
                "https://example.com/terms-of-service",
                "Terms of service",
                new Contact("Daniel Jaramillo", "https://www.linkedin.com/in/daniel-jaramillo-m", "jarasoftware@gmail.com"),
                "MIT Licence", "https://opensource.org/licences/MIT",
                Collections.emptyList());
    }
}






