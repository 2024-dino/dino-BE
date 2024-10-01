package khu.dino.question.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.persistence.Event;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.question.QuestionException;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import khu.dino.question.persistence.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

@Adapter
@RequiredArgsConstructor
public class QuestionQueryAdapter {
    private final QuestionRepository questionRepository;

    public List<Question> occurredAtBetweenStartDateAndEndDate(PrincipalDetails principalDetails, LocalDate startDate, LocalDate endDate) {
        Specification<Question> spec = QuestionSpecification.occurredAtBetweenStartDateAndEndDate(principalDetails.getMember().getId(),startDate, endDate);
        return questionRepository.findAll(spec);
    }

    public List<Question> findByMemberAndEvent(Member member, Event event){
        return questionRepository.findByOwnerAndEvent(member, event);
    }

    public List<Question> getHiStoryQuestion(PrincipalDetails principalDetails) {
        return questionRepository.findAllByOwnerAndIsPriorityIsTrueOrderByOccurredAtAsc(principalDetails.getMember());
    }

    public Question getQuestionByIdAndOwner(Long questionId, Member member) {
        return questionRepository.findQuestionByIdAndOwner(questionId, member).orElseThrow( () -> new QuestionException(ErrCode.QUESTION_NOT_FOUND.getMessage()));
    }
}
