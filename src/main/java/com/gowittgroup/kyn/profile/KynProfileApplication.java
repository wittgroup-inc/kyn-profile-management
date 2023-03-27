package com.gowittgroup.kyn.profile;

import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootApplication
@EnableFeignClients
public class KynProfileApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KynProfileApplication.class, args);
    }

    @Bean
    RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            requestTemplate.header("Authorization", "Bearer " + token.getToken().getTokenValue());
        };
    }
}
