package khu.dino.answer.business;

import khu.dino.answer.implement.AnswerCommandAdapter;
import khu.dino.answer.implement.AnswerQueryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerCommandAdapter answerCommandAdapter;
    private final AnswerQueryAdapter answerQueryAdapter;
}
