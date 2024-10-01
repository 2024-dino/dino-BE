package khu.dino.event.persistence.repository;

import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {









    List<Event> findAllByCreatorIdAndEventStatus(Long creatorId, Status eventStatus);

}
