package khu.dino.common.openai;


import khu.dino.common.config.OpenAIFeignConfig;
import khu.dino.common.openai.dto.OpenAIRequestDto;
import khu.dino.common.openai.dto.OpenAIResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "openai-client", url = "https://api.openai.com", configuration = OpenAIFeignConfig.class)
public interface OpenAIFeignClient {

    @PostMapping(value = "/v1/chat/completions")
    OpenAIResponseDto.OpenAIResponse sendRequest(@RequestBody OpenAIRequestDto.OpenAIRequest request);

}
