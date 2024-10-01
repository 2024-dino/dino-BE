package khu.dino.answer.presentation.dto;

import khu.dino.answer.persistence.enums.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerRequestDto {
    @Getter
    public static class writeAnswerDto{
        private String content;
        private Type type;
    }

}
