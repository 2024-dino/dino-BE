package khu.dino.question.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.question.persistence.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class QuestionCommandAdapter {
    private final QuestionRepository questionRepository;
}