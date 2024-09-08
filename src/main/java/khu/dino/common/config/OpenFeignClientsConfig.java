package khu.dino.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

@Component
@EnableFeignClients(basePackages = "khu.dino")
public class OpenFeignClientsConfig {
}
