package com.bai.guli.infrastructure.apigatway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {
        @Bean
        public CorsWebFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedMethod("*");  //表示允许 任何方法跨域
            config.addAllowedOrigin("*");  //表示允许任何源跨域
            config.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser()); //添加跨域设置并设置初始化跨域规则
            source.registerCorsConfiguration("/**", config); //表示任何路径都允许跨域
            return new CorsWebFilter(source);
        }
}
