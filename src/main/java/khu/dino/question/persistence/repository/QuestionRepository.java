package khu.dino.question.persistence.repository;

import khu.dino.question.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
