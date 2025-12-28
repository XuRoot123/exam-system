package cn.com.xuroot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://192.168.241.231:8000", "http://localhost:8000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}