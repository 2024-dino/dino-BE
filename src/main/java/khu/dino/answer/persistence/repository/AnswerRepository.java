package khu.dino.answer.persistence.repository;

import khu.dino.answer.persistence.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
