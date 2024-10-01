package khu.dino.answer.business;

import khu.dino.answer.persistence.Answer;
import khu.dino.answer.presentation.dto.AnswerRequestDto;
import khu.dino.common.annotation.Mapper;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Mapper
@Slf4j
@RequiredArgsConstructor
public class AnswerMapper {

    public static Answer toAnswer(Member member, Question question, AnswerRequestDto.writeAnswerDto request) {
       return Answer.builder()
                .question(question)
                .content(request.getContent())
                .owner(member)
                .build();

    }
}
