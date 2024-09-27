package khu.dino.event.business;

import khu.dino.common.annotation.Mapper;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import khu.dino.event.presentation.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;


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
