
package khu.dino.common.config;


import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;


@Configuration
public class OpenAIFeignConfig {

    @Value("${openai.api.key}")
    private String openAIKey;


    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + openAIKey);
            requestTemplate.header("Content-Type", "application/json;charset=utf-8");
            requestTemplate.header("Accept-Charset", StandardCharsets.UTF_8.name());
        };
    }



}

