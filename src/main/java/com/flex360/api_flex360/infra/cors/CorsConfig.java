package com.flex360.api_flex360.infra.cors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.flex360.api_flex360.infra.dotenv.DotenvConfig;

import org.springframework.lang.NonNull;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CorsConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CorsConfig.class);
    private final Dotenv dotenv;

    public CorsConfig(DotenvConfig dotenvConfig) {
        this.dotenv = dotenvConfig.dotenv();
    }
    
    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                //String allowedOrigins = dotenv.get("HTTP_ORIGIN", "http://localhost:5173");
                String allowedOrigins = "http://flex360-front-ae8fh.s3-website-us-east-1.amazonaws.com";
                logger.info("Configuring CORS with allowed origins: {}", allowedOrigins);

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods(allowedMethods.split(","))
                        .allowedHeaders(allowedHeaders.split(","))
                        .allowCredentials(allowCredentials);
            }
        };
    }
    
}
