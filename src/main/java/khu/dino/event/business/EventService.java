package khu.dino.event.business;

import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.event.EventException;
import khu.dino.common.openai.OpenAIUtil;
import khu.dino.event.implement.EventCommandAdapter;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import khu.dino.event.persistence.Event;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.question.business.QuestionMapper;
import khu.dino.question.implement.QuestionCommandAdapter;
import khu.dino.question.implement.QuestionQueryAdapter;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionRequestDto;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {

    private final EventCommandAdapter eventCommandAdapter;
    private final EventQueryAdapter eventQueryAdapter;
    private final QuestionQueryAdapter questionQueryAdapter;
    private final QuestionCommandAdapter questionCommandAdapter;

    private final EventMapper eventMapper;
    private final QuestionMapper questionMapper;
    private final OpenAIUtil openAIUtil;

    @Transactional(readOnly = false)
    public void saveNewEvent(Member member, EventRequestDto.saveEventDto request) throws Exception {
        log.info(member.toString());

        // 이벤트 저장
        Event event = eventCommandAdapter.save(EventMapper.toEvent(member, request));

        // 이벤트에 해당하는 질문 생성 및 푸시 알림 등록
        List<QuestionRequestDto.questionSimpleInfoDto> questionList = openAIUtil.createQuestionList(request, member, event);

        // 생성된 질문 db에 저장
        questionCommandAdapter.saveList(QuestionMapper.createQuestionDtoListToQuestionList(questionList, event, member));

    }

    public EventResponseDto.eventDetailDto getEventDetailInfo(Member member, Long eventId){
        Event event = eventQueryAdapter.findById(eventId);
        List<Question> questionList = questionQueryAdapter.findByMemberAndEvent(member, event);

        return EventMapper.toEventDetailDto(event, questionList);
    }


    public List<EventResponseDto.MainEvent> getMainEvent(PrincipalDetails principalDetails) {
        List<Event> eventList = eventQueryAdapter.findMainEvent(principalDetails);


        return eventList.stream()
                .map(event -> {
                    EventResponseDto.MainEvent mainEvent = eventMapper.toMainEventList(event);
                    List<QuestionResponseDto.QuestionContent> QuestionContents = event.getQuestionList().stream()
                            .map(questionMapper::toQuestionContent).toList();
                    long isAnswerCount = QuestionContents.stream()
                            .filter(QuestionResponseDto.QuestionContent::getIsAnswer)
                            .count();
                    //Double answerRate = QuestionContents.isEmpty() ? 0 : ((double) isAnswerCount / QuestionContents.size()) * 100;
                    mainEvent.setQuestionContent(QuestionContents);
                    mainEvent.setTotalAnswerCount(isAnswerCount);
                    mainEvent.setTotalQuestionCount((long) QuestionContents.size());

                    return mainEvent;
                }).toList();
    }

    @Transactional(readOnly = false)
    public void modifyEventInfo(Member member, Long eventId, EventRequestDto.modifyEventInfoDto request){
        Event event = eventQueryAdapter.findById(eventId);
        eventCommandAdapter.update(event, request);
    }

    public List<EventResponseDto.EventInfo> getEvents(String status, Member member) {

        log.info(status.equals(Status.EXECUTION.name()) ? "진행중인 이벤트 조회" : "완료된 이벤트 조회");
        List<Event> eventList = eventQueryAdapter.findAllByOwnerIdAndStatus(member.getId(), status.equals(Status.EXECUTION.toString()) ? Status.EXECUTION : Status.TERMINATION);


        return eventList.stream()
                .map(event -> {
                    EventResponseDto.EventInfo eventInfo = eventMapper.toEventDetail(event);
                    QuestionResponseDto.QuestionContent representativeQuestion = event.getRepresentativeQuestion() != null ? questionMapper.toQuestionContent(event.getRepresentativeQuestion()) : null;
                    List<QuestionResponseDto.QuestionContent> QuestionContents = event.getQuestionList().stream()
                            .map(questionMapper::toQuestionContent).toList();
                    long isAnswerCount = QuestionContents.stream()
                            .filter(QuestionResponseDto.QuestionContent::getIsAnswer)
                            .count();
                    //Double answerRate = QuestionContents.isEmpty() ? 0 : ((double) isAnswerCount / QuestionContents.size()) * 100;
                    eventInfo.setRepresentativeQuestion(representativeQuestion);
                    eventInfo.setTotalAnswerCount(isAnswerCount);
                    eventInfo.setTotalQuestionCount((long) QuestionContents.size());
                    return eventInfo;
                }).toList();
    }

    @Transactional(readOnly = false)
    public void setRepresentativeQuestion(Long eventId, Long questionId){
        Event event = eventQueryAdapter.findById(eventId);
        List<Question> questionList = event.getQuestionList();
        Question question = questionQueryAdapter.findById(questionId);
        if(!questionList.contains(question)){
            log.error("해당 질문은 해당 이벤트의 질문이 아닙니다.");
            throw new EventException(ErrCode.INVALID_QUESTION_REQUEST.getMessage());
        }
        event.setRepresentativeQuestion(question);
    }
}
