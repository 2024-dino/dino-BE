package khu.dino.question.implement;

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
}
