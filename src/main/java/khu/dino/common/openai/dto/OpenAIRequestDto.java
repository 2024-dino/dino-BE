package khu.dino.common.openai.dto;

import lombok.*;
import org.quartz.SchedulerException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAIRequestDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class OpenAIRequest{
        private String model;
        private List<OpenAIRequestMessage> messages;
    }



    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class OpenAIRequestMessage{
        private String role; //역할
        private String content; //내용

    }
}
