package khu.dino.event.business;

import khu.dino.common.annotation.Mapper;
import khu.dino.event.persistence.Event;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Mapper
@Slf4j
@RequiredArgsConstructor
public class EventMapper {
    public static Event toEvent(Member member, EventRequestDto.saveEventDto request) {
        return Event.builder()
                .title(request.getTitle())
                .memo(request.getMemo())
                .occurrenceTime(request.getOccurrenceTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .emotion(request.getEmotion())
                .totalQuestionCount(request.getQuestionSize())
                .build();
    }
}
