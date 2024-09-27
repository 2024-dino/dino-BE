package khu.dino.question.implement;

import khu.dino.question.persistence.Question;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class QuestionSpecification {

    public static Specification<Question> occurredAtBetweenStartDateAndEndDate(Long ownerId, LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("occurredAt"), startDate, endDate),
                        criteriaBuilder.equal(root.get("owner").get("id"), ownerId)
                );
    }
}
