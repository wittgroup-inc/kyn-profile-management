package com.wittgroup.kyn.profile;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.web.servlet.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.io.IOException;

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
