package khu.dino.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:60000",  "https://khu-dino.kro.kr:3000", "https://khu-dino.n-e.kr:443", "https://khu-dino.n-e.kr:80", "https://khu-dino.n-e.kr:3000", "https://khu-dino.n-e", "https://khu-dino.n-e.kr:60000")
                .allowedHeaders("authorization", "User-Agent", "Cache-Control", "Content-Type")
                .exposedHeaders("authorization", "User-Agent", "Cache-Control", "Content-Type")
                .allowedMethods("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/").setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }
}
