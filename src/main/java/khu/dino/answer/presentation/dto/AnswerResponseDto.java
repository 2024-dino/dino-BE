package khu.dino.answer.presentation.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import khu.dino.answer.persistence.enums.Type;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class questionAndAnswerDto{
        private Long questionId;
        private String questionContent;
        private boolean isAnswered;
        private String answerContent;

        @Enumerated(EnumType.STRING)
        private Type answerType;

        private String answerFileUrl;
        private String answerFileName;
    }
}
