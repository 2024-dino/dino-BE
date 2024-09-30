package khu.dino.event.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.event.EventException;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.persistence.repository.EventRepository;
import khu.dino.member.persistence.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class EventQueryAdapter {
    private final EventRepository eventRepository;

    public List<Event> findMainEvent(PrincipalDetails principalDetails) {
        return eventRepository.findAllByCreatorIdAndEventStatus(principalDetails.getMember().getId(), Status.EXECUTION);
    }

    public Event findById(Long eventId){
        return eventRepository.findById(eventId).orElseThrow(() -> new EventException(ErrCode.EVENT_NOT_FOUND.getMessage()));
    }

    public List<Event> findAllByOwnerIdAndStatus(Long ownerId, Status status) {
        Specification<Event> eventSpecification = EventSpecification.findAllByOwnerIdAndStatus(ownerId, status);
        return eventRepository.findAll(eventSpecification);
    }

}
