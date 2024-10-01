package khu.dino.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.event.persistence.enums.Status;
import khu.dino.growthObject.persistence.enums.Category;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionRequestDto;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public static class eventDetailDto {
        @Builder.Default
        Long eventId = 0L;
        @Builder.Default
        String title = "";
        //        @Builder.Default
//        Category category = null;
        @Builder.Default
        Emotion emotion = null;

        String memo;
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
        Long totalAnswerCount = 0L;

        @Schema(type = "string", example = "14:30")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime occurrenceTime;

        QuestionResponseDto.questionContent representativeQuestion;

        @Builder.Default
        List<QuestionResponseDto.questionContent> questionContent = new ArrayList<>();
    }

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
//        @Builder.Default
//        Step step = null;
        @Builder.Default
        Long totalQuestionCount = 0L;
        @Builder.Default
        Long totalAnswerCount = 0L;
        @Builder.Default
        List<QuestionResponseDto.questionContent> questionContent = new ArrayList<>();
    }




}
