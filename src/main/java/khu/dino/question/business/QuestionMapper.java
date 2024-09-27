package khu.dino.question.business;

import khu.dino.common.annotation.Mapper;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Mapper
@Slf4j
@RequiredArgsConstructor
public class QuestionMapper {
    public QuestionResponseDto.questionContent toQuestionContent(Question question, Integer sequence) {
        if(question.getAnswer() == null) {
             return QuestionResponseDto.questionContent.builder()
                     .questionId(question.getId())
                     .content(question.getContent())
                     .questionDate(question.getOccurredAt())
                     .sequence(sequence)
                     .isAnswer(false)
                     .build();
        }else{
            return  QuestionResponseDto.questionContent.builder()
                    .questionId(question.getId())
                    .content(question.getContent())
                    .questionDate(question.getOccurredAt())
                    .type(question.getAnswer().getType())
                    .isAnswer(true)
                    .myAnswer(question.getAnswer().getContent())
                    .fileUrl(question.getAnswer().getFileUrl())
                    .sequence(sequence)
                    .build();
        }
    }

    public QuestionResponseDto.EventContent toEventContent(Question question) {
        if(question.getAnswer() == null) {
            return QuestionResponseDto.EventContent.builder()
                    .eventId(question.getEvent().getId())
                    .title(question.getEvent().getTitle())
                    .emotion(question.getEvent().getEmotion())
                    .eventStatus(question.getEvent().getEventStatus())
                    .questionId(question.getId())
                    .content(question.getContent())
                    .isAnswer(false)
                    .build();
        }else{
            return  QuestionResponseDto.EventContent.builder()
                    .eventId(question.getEvent().getId())
                    .title(question.getEvent().getTitle())
                    .emotion(question.getEvent().getEmotion())
                    .eventStatus(question.getEvent().getEventStatus())
                    .questionId(question.getId())
                    .content(question.getContent())
                    .isAnswer(true)
                    .isPriority(question.getIsPriority())
                    .fileUrl(question.getAnswer().getFileUrl())
                    .type(question.getAnswer().getType())
                    .myAnswer(question.getAnswer().getContent())
                    .build();
        }
    }
}
