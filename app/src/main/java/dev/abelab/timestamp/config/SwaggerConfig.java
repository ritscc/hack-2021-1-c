package dev.abelab.timestamp.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import dev.abelab.timestamp.property.TimeStampProperty;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    TimeStampProperty timeStampProperty;

    @Bean
    public Docket dock() {
        return new Docket(DocumentationType.SWAGGER_2) //
            .useDefaultResponseMessages(false) //
            .protocols(Collections.singleton(this.timeStampProperty.getProtocol())) //
            .host(this.timeStampProperty.getHostname()) //
            .select() //
            .apis(RequestHandlerSelectors.basePackage("dev.abelab.timestamp.api.controller.internal")) //
            .build() //
            .apiInfo(apiInfo()) //
            .tags( //
                new Tag("Auth", "認証"), //
                new Tag("User", "ユーザ"), //
                new Tag("Stamp", "スタンプ") //
            );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() //
            .title("Time Stamp Internal API") //
            .version("1.0") //
            .build();
    }
}
