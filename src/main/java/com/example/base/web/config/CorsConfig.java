package com.example.base.web.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    private final String[] allowedOrigins = {
            "http://localhost:3000",
            "https://admin.donggo-dlock.site",
            "https://www.donggo-dlock.site",
            "https://donggo-dlock.site",
    };

    private final String[] allowedMethods = {
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "OPTIONS"
    };

    private final String[] allowedHeaders = {
            "Origin",
            "Content-Type",
            "Accept",
            "Authorization",
            "X-Requested-With",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Access-Control-Allow-Headers",
            "Access-Control-Allow-Methods",
            "Access-Control-Expose-Headers",
            "Access-Control-Max-Age",
            "Access-Control-Request-Headers",
            "Access-Control-Request-Method",
            "withCredentials",
            "Cache-Control",
            "Content-Length",
            "Cookie",
            "Host",
            "Set-Cookie",
            "User-Agent"
    };

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.stream(allowedOrigins).toList());
        config.setAllowedHeaders(Arrays.stream(allowedHeaders).toList());
        config.setAllowedMethods(Arrays.stream(allowedMethods).toList());
        config.addExposedHeader("Set-Cookie");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}