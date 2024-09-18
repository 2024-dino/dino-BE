package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.event.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class EventQueryAdapter {
    private final EventRepository eventRepository;
}
