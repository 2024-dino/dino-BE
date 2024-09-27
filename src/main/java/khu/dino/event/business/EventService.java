package khu.dino.event.business;

import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.implement.EventCommandAdapter;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import khu.dino.event.persistence.Event;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.question.business.QuestionMapper;
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

    private final EventMapper eventMapper;
    private final QuestionMapper questionMapper;

    @Transactional(readOnly = false)
    public Long saveNewEvent(Member member, EventRequestDto.saveEventDto request){
        log.info(member.toString());
        return eventCommandAdapter.save(EventMapper.toEvent(member, request));
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

}
