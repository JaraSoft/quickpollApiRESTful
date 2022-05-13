package com.jarasoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/v1/*.*"))
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
                .paths(PathSelectors.regex("/api/v2/*.*"))
                .build()
                .apiInfo(apiInfo("v2"))
                .groupName("v2")
                .useDefaultResponseMessages(false)
                ;
    }

    @Bean
    public Docket apiV3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/v3/*.*"))
                .build()
                .apiInfo(apiInfo("v3"))
                .groupName("v3")
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfo(
                "Quickpoll REST Api",
                "Quickpoll Api for creating and managing polls",
                "https://example.com/terms-of-service",
                "Terms of service",
                new Contact("Daniel Jaramillo", "https://www.linkedin.com/in/daniel-jaramillo-m", "jarasoftware@gmail.com"),
                "MIT Licence", "https://opensource.org/licences/MIT",
                Collections.emptyList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}






