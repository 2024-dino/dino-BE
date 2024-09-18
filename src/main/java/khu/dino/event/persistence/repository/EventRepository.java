package khu.dino.event.persistence.repository;

import khu.dino.event.persistence.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
