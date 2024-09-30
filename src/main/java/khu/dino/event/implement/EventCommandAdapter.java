package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.repository.EventRepository;
import khu.dino.event.presentation.dto.EventRequestDto;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class EventCommandAdapter {
    private final EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void update(Event event, EventRequestDto.modifyEventInfoDto request) {
        event.update(request);
    }
}
