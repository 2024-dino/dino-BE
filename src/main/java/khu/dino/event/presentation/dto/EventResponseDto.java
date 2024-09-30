package khu.dino.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import khu.dino.answer.persistence.enums.Type;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.event.persistence.enums.Status;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDto {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainEvent {
        @Builder.Default
        Long eventId = 0L;
        @Builder.Default
        String title = "";
//        @Builder.Default
//        Category category = null;
        @Builder.Default
        Emotion emotion = null;
        @Builder.Default
        Status eventStatus = null;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @Builder.Default
        String fileUrl = "";
        @Builder.Default
        String memo = "";
//        @Builder.Default
//        Step step = null;
        @Builder.Default
        Long totalQuestionCount = 0L;
        @Builder.Default
        Long totalAnswerCount = 0L;
        @Builder.Default
        List<QuestionResponseDto.QuestionContent> questionContent = new ArrayList<>();
    }


    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventInfo {
        @Builder.Default
        Long eventId = 0L;
        @Builder.Default
        String title = "";
        @Builder.Default
        Emotion emotion = null;
        @Builder.Default
        Status eventStatus = null;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @Builder.Default
        String fileUrl = "";
        @Builder.Default
        Long totalQuestionCount = 0L;
        @Builder.Default
        String memo = "";
        @Builder.Default
        Long totalAnswerCount = 0L;
        @Builder.Default
        QuestionResponseDto.QuestionContent representativeQuestion = null;

    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDetail {
        @Builder.Default
        Long eventId = 0L;
        @Builder.Default
        String title = "";
        @Builder.Default
        Emotion emotion = null;
        @Builder.Default
        Status eventStatus = null;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        @Builder.Default
        String fileUrl = "";
        @Builder.Default
        Long totalQuestionCount = 0L;
        @Builder.Default
        String memo = "";
        @Builder.Default
        Long totalAnswerCount = 0L;
        @Builder.Default
        QuestionResponseDto.QuestionContent representativeQuestion = null;
        @Builder.Default
        List<QuestionResponseDto.QuestionContent> questionContent = new ArrayList<>();
    }



    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventContent {
        @Builder.Default
        private Long eventId = 0L;
        @Builder.Default
        private String title = null;
//        @Builder.Default
//        private Step step = null;
        @Builder.Default
        private Emotion emotion = null;
        @Builder.Default
        private Status eventStatus = null;
        @Builder.Default
        private Long questionId = 0L;
        @Builder.Default
        private String content = "";
        @Builder.Default
        private String memo = "";
        @Builder.Default
        private Boolean isAnswer = false;
        @Builder.Default
        private String myAnswer = null;
        @Builder.Default
        private Boolean isPriority = false;
        @Builder.Default
        private String fileUrl = null;
        @Builder.Default
        private Type type = null;
    }

}
