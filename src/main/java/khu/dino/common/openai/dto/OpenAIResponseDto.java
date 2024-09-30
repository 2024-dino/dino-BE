package khu.dino.common.openai.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAIResponseDto {


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class OpenAIResponse{
        private List<OpenAIResponseDto.OpenAIChoice> choices;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class OpenAIChoice{
        private int index;
        private OpenAIResponseMessage message;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class OpenAIResponseMessage{
        private String role; //역할
        private String content; //내용

    }
}
