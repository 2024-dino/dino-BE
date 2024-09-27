package khu.dino.event.business;

import khu.dino.event.implement.EventCommandAdapter;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventCommandAdapter eventCommandAdapter;
    private final EventQueryAdapter eventQueryAdapter;

    @Transactional(readOnly = false)
    public Long saveNewEvent(Member member, EventRequestDto.saveEventDto request){
        return eventCommandAdapter.save(EventMapper.toEvent(member, request));
    }
}
