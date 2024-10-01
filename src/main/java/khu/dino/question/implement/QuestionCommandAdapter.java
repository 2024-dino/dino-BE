package khu.dino.question.implement;

import jakarta.transaction.Transactional;
import khu.dino.common.annotation.Adapter;
import khu.dino.question.persistence.Question;
import khu.dino.question.persistence.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class QuestionCommandAdapter {
    private final QuestionRepository questionRepository;


    public void save(Question question){
        questionRepository.save(question);
    }
    public void saveList(List<Question> questions){
        questionRepository.saveAll(questions);
    }

    @Transactional
    public void setIsAnswered(Question question, boolean answered) {
        question.updateIsAnswered(answered);
    }

    @Transactional
    public void setQuestionBookmarkStatus(Question question, Boolean priority) {
        question.updatePriorityStatus(priority);
    }


}
