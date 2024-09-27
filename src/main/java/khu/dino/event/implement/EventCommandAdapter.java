package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class EventCommandAdapter {
    private final EventRepository eventRepository;

    public Long save(Event event) {
        return eventRepository.save(event).getId();
    }
}
