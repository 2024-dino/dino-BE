package khu.dino.question.persistence.repository;

import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question>{

    List<Question> findByOwnerAndEvent(Member owner, Event event);

}
