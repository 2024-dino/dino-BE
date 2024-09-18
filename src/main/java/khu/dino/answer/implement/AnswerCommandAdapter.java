package khu.dino.answer.implement;

import khu.dino.answer.persistence.repository.AnswerRepository;
import khu.dino.common.annotation.Adapter;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class AnswerCommandAdapter {
    private final AnswerRepository answerRepository;
}
