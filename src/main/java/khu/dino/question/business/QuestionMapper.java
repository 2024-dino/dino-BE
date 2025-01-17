package khu.dino.question.business;

import khu.dino.answer.presentation.dto.AnswerResponseDto;
import khu.dino.common.annotation.Mapper;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionRequestDto;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Slf4j
@RequiredArgsConstructor
public class QuestionMapper {


    public static List<Question> createQuestionDtoListToQuestionList(List<QuestionRequestDto.questionSimpleInfoDto> questionInfoList, Event event, Member member){
        List<Question> questionList = new ArrayList<>();

        questionInfoList.stream()
                .map(questionInfo -> createQuestionDtoToQuestion(event, member, questionInfo))
                .forEach(questionList::add);

        return questionList;
    }

    public static Question createQuestionDtoToQuestion(Event event, Member member, QuestionRequestDto.questionSimpleInfoDto request){
        return Question.builder()
                .content(request.getContent())
                .event(event)
                .owner(member)
                .occurredAt(request.getOccurredAt())
                .build();
    }


    public QuestionResponseDto.QuestionContent toQuestionContent(Question question) {

        if(!question.getIsAnswered()) {
             return QuestionResponseDto.QuestionContent.builder()
                     .questionId(question.getId())
                     .content(question.getContent())
                     .questionDate(question.getOccurredAt())
                     .isAnswer(question.getIsAnswered())
                     .eventTitle(question.getEvent().getTitle())
                     .build();
        }else{
            return  QuestionResponseDto.QuestionContent.builder()
                    .questionId(question.getId())
                    .content(question.getContent())
                    .questionDate(question.getOccurredAt())
                    .isAnswer(question.getIsAnswered())
                    .myAnswer(question.getAnswer().getContent())
                    .answeredAt(question.getAnswer().getCreatedAt().toLocalDate())
                    .fileUrl(question.getAnswer().getFileUrl())
                    .eventTitle(question.getEvent().getTitle())
                    .build();
        }
    }

    public static QuestionResponseDto.QuestionContent questionToQuestionContent(Question question) {
        if(question.getAnswer() == null) {
            return QuestionResponseDto.QuestionContent.builder()
                    .questionId(question.getId())
                    .content(question.getContent())
                    .questionDate(question.getOccurredAt())
                    .isPriority(question.getIsPriority())
                    .isAnswer(false)
                    .eventTitle(question.getEvent().getTitle())
                    .build();
        }else{
            return  QuestionResponseDto.QuestionContent.builder()
                    .questionId(question.getId())
                    .content(question.getContent())
                    .questionDate(question.getOccurredAt())
                    .type(question.getAnswer().getType())
                    .isAnswer(true)
                    .myAnswer(question.getAnswer().getContent())
                    .answeredAt(question.getAnswer().getCreatedAt().toLocalDate())
                    .fileUrl(question.getAnswer().getFileUrl())
                    .isPriority(question.getIsPriority())
                    .eventTitle(question.getEvent().getTitle())
                    .build();
        }
    }

    public static List<QuestionResponseDto.QuestionContent> questionListToQuestionContentList(List<Question> questionList) {
        List<QuestionResponseDto.QuestionContent> questionContentList = new ArrayList<>();

        questionList.stream()
                .map(question -> questionToQuestionContent(question))
                .forEach(questionContentList::add);
        return questionContentList;
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
                    .build();
        }
    }

    public static List<AnswerResponseDto.questionAndAnswerDto> questionListToQuestionAndAnswerDtoList(List<Question> questionList) {
        List<AnswerResponseDto.questionAndAnswerDto> questionAndAnswerDtoList = new ArrayList<>();

        questionList.stream()
                .map(question -> questionToQuestionAndAnswerDto(question))
                .forEach(questionAndAnswerDtoList::add);

        return questionAndAnswerDtoList;

    }

    public static AnswerResponseDto.questionAndAnswerDto questionToQuestionAndAnswerDto(Question question) {

        if(!question.isAnswered) {
            return AnswerResponseDto.questionAndAnswerDto.builder()
                    .questionId(question.getId())
                    .questionContent(question.getContent())
                    .isAnswered(false)
                    .answerContent("")
                    .answerFileUrl("")
                    .answerFileName("")
                    .eventTitle(question.getEvent().getTitle())
                    .build();
        }
        else return AnswerResponseDto.questionAndAnswerDto.builder()
                .questionId(question.getId())
                .questionContent(question.getContent())
                .isAnswered(true)
                .answerContent(question.getAnswer().getContent())
                .answeredAt(question.getAnswer().getCreatedAt().toLocalDate())
                .answerFileUrl(question.getAnswer().getFileUrl())
                .answerFileName(question.getAnswer().getFileName())
                .eventTitle(question.getEvent().getTitle())
                .build();
    }
}
