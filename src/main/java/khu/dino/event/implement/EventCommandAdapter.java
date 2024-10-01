package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.repository.EventRepository;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.growthObject.implement.GrowthObjectQueryAdapter;
import khu.dino.growthObject.persistence.GrowthObject;
import khu.dino.growthObject.persistence.repository.GrowthObjectRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class EventCommandAdapter {
    private final EventRepository eventRepository;
    private final GrowthObjectRepository growthObjectRepository;

    public Event save(Event event) {
        GrowthObject growthObject = growthObjectRepository.findById(1L).get();
        event.setGrowthObject(growthObject);
        return eventRepository.save(event);
    }
    public void update(Event event, EventRequestDto.modifyEventInfoDto request) {
        event.update(request);
    }
}
