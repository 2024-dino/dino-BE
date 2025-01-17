package khu.dino.question.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import khu.dino.answer.persistence.enums.Type;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.presentation.dto.EventResponseDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionContent {
        @Builder.Default
        Long questionId = 0L;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Builder.Default
        LocalDate questionDate = null;

        @Builder.Default
        Boolean isAnswer = false;
        @Builder.Default
        Boolean isPriority = false;
        @Builder.Default
        String content = null;

        @Builder.Default
        String myAnswer = null;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate answeredAt;
        @Builder.Default
        String fileUrl = null; //답변 파일
        @Builder.Default
        Type type = null;

        @Builder.Default
        String eventTitle = null;


    }



    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalendarEvent{

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private String eventDate;
        @Builder.Default
        List<EventResponseDto.EventContent> eventContent = new ArrayList<>();
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
        @Builder.Default
        private Step step = null;
        @Builder.Default
        private Emotion emotion = null;
        @Builder.Default
        private Status eventStatus = null;
        @Builder.Default
        private Long questionId = 0L;
        @Builder.Default
        private String content = "";
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


    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class PriorityQuestion {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
        private String groupByDate;
        @Builder.Default
        List<QuestionContent> questionContent = new ArrayList<>();
    }
}
