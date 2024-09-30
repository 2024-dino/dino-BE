package khu.dino.question.presentation.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionRequestDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class questionSimpleInfoDto{
        private String content;
        private LocalDate occurredAt;
    }
}
