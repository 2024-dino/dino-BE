package khu.dino.question.persistence.repository;


import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question>{

    List<Question> findByOwnerAndEvent(Member owner, Event event);

    List<Question> findAllByOwnerAndIsPriorityIsTrueOrderByOccurredAtAsc(Member member);

    Optional<Question> findQuestionByIdAndOwner(Long questionId, Member owner );



}
