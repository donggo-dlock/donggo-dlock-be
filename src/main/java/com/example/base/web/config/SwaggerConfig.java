package com.example.base.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${springdoc.version}")
    private String serverVersion;
    @Value("${spring.config.activate.on-profile}")
    private String springProfile;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(this.apiInfo())
                ;
    }

    private Info apiInfo() {
        return new Info()
                .title("[" + springProfile + "] " + "Api Server ")
                .version(serverVersion)
                ;
    }
}
