package khu.dino.answer.implement;

import khu.dino.answer.persistence.Answer;
import khu.dino.answer.persistence.repository.AnswerRepository;
import khu.dino.common.annotation.Adapter;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class AnswerCommandAdapter {
    private final AnswerRepository answerRepository;

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }
}
