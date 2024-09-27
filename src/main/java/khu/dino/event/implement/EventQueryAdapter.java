package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class EventQueryAdapter {
    private final EventRepository eventRepository;

    public List<Event> findMainEvent(PrincipalDetails principalDetails) {
        return eventRepository.findAllByCreatorIdAndEventStatus(principalDetails.getMember().getId(), Status.EXECUTION);
    }
}
