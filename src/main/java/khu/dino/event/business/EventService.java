package khu.dino.event.business;

import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.openai.OpenAIUtil;
import khu.dino.event.implement.EventCommandAdapter;
import khu.dino.event.implement.EventQueryAdapter;
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
import java.util.concurrent.atomic.AtomicReference;

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

        AtomicReference<Integer> sequence = new AtomicReference<>(1);

        return eventList.stream()
                .map(event -> {
                    EventResponseDto.MainEvent mainEvent = eventMapper.toMainEventList(event);
                    List<QuestionResponseDto.questionContent> questionContents = event.getQuestionList().stream()
                            .map(question -> {
                                return questionMapper.toQuestionContent(question, sequence.getAndSet(sequence.get() + 1));
                            }).toList();
                    long isAnswerCount = questionContents.stream()
                            .filter(QuestionResponseDto.questionContent::getIsAnswer)
                            .count();
                    //Double answerRate = questionContents.isEmpty() ? 0 : ((double) isAnswerCount / questionContents.size()) * 100;
                    mainEvent.setQuestionContent(questionContents);
                    mainEvent.setTotalAnswerCount(isAnswerCount);
                    mainEvent.setTotalQuestionCount((long) questionContents.size());

                    return mainEvent;
                }).toList();
    }

    @Transactional(readOnly = false)
    public void modifyEventInfo(Member member, Long eventId, EventRequestDto.modifyEventInfoDto request){
        Event event = eventQueryAdapter.findById(eventId);
        eventCommandAdapter.update(event, request);
    }

}
