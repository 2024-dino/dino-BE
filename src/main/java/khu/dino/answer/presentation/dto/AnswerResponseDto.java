package khu.dino.answer.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import khu.dino.answer.persistence.enums.Type;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate answeredAt;

        @Builder.Default
        String eventTitle = null;

        @Enumerated(EnumType.STRING)
        private Type answerType;

        private String answerFileUrl;
        private String answerFileName;
    }
}
