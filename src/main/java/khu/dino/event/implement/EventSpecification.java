package khu.dino.event.implement;

import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.enums.Status;
import org.springframework.data.jpa.domain.Specification;


public class EventSpecification {

    public static Specification<Event> findAllByOwnerIdAndStatus(Long ownerId, Status status) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(
                    status == Status.EXECUTION
                            ? criteriaBuilder.desc(root.get("startDate"))
                            : criteriaBuilder.desc(root.get("endDate"))
            );


            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("status"), status),
                    criteriaBuilder.equal(root.get("owner").get("id"), ownerId)
            );
        };
    }
}
