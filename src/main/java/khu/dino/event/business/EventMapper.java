package khu.dino.event.business;

import khu.dino.common.annotation.Mapper;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.question.business.QuestionMapper;
import khu.dino.question.persistence.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Mapper
@Slf4j
@RequiredArgsConstructor
public class EventMapper {

    public static Event toEvent(Member member, EventRequestDto.saveEventDto request) {
        return Event.builder()
                .title(request.getTitle())
                .memo(request.getMemo())
                .creator(member)
                .occurrenceTime(request.getOccurrenceTime())
                .startDate(request.getStartDate())
                .eventStatus(Status.EXECUTION)
                .endDate(request.getEndDate())
                .emotion(request.getEmotion())
                .totalQuestionCount(request.getQuestionSize())
                .build();
    }

    public static EventResponseDto.eventDetailDto toEventDetailDto(Event event, List<Question> questionList) {
        Long totalQuestionCount = 0L;
        for(Question question : questionList) {
            if(question.isAnswered()) totalQuestionCount++;
        }

        return EventResponseDto.eventDetailDto.builder()
                .title(event.getTitle())
                .emotion(event.getEmotion())
                .eventStatus(event.getEventStatus())
                .startDate(event.getStartDate().toString())
                .memo(event.getMemo())
                .endDate(event.getEndDate().toString())
                .fileUrl(event.getGrowthObject().getFileUrl())
                .totalQuestionCount(event.getTotalQuestionCount())
                .totalAnswerCount(totalQuestionCount)
                .occurrenceTime(event.getOccurrenceTime())
                .representativeQuestion(QuestionMapper.questionToQuestionContent(event.getRepresentativeQuestion()))
                .questionContent(QuestionMapper.questionListToQuestionContentList(questionList))
                .build();
    }



    public EventResponseDto.MainEvent toMainEventList(Event eventEntity) {

        return  EventResponseDto.MainEvent.builder()
                            .eventId(eventEntity.getId())
                            .eventStatus(eventEntity.getEventStatus())
                            .title(eventEntity.getTitle())
                            .fileUrl(eventEntity.getGrowthObject().getFileUrl())
                            .emotion(eventEntity.getEmotion())
                            .startDate(eventEntity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .endDate(eventEntity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .build();
    }
}
