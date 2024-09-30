package khu.dino.question.business;

import khu.dino.answer.presentation.dto.AnswerResponseDto;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import khu.dino.question.implement.QuestionQueryAdapter;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionQueryAdapter questionQueryAdapter;

    private final QuestionMapper questionMapper;
    private final EventQueryAdapter eventQueryAdapter;

    public List<QuestionResponseDto.CalendarEvent> getCalendarEvent(PrincipalDetails principalDetails, String requestMonth) {
        YearMonth yearMonth = YearMonth.parse(requestMonth);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();


        List<Question> questions = questionQueryAdapter.occurredAtBetweenStartDateAndEndDate(principalDetails, startDate, endDate);

        Map<LocalDate, List<QuestionResponseDto.EventContent>> groupedQuestions = questions.stream()
                .collect(Collectors.groupingBy(
                        Question::getOccurredAt,
                        Collectors.mapping(questionMapper::toEventContent,
                            Collectors.toList())
                ));

        return groupedQuestions.entrySet().stream().map(entry -> QuestionResponseDto.CalendarEvent.builder()
                .eventDate(entry.getKey())
                .eventContent(entry.getValue())
                .build())
                .collect(Collectors.toList());
    }

    public List<AnswerResponseDto.questionAndAnswerDto> getQuestionAndAnswerList(PrincipalDetails principalDetails, Long eventId){
        Member member = principalDetails.getMember();
        Event event = eventQueryAdapter.findById(eventId);

        List<Question> questionList = questionQueryAdapter.findByMemberAndEvent(member, event);

        return QuestionMapper.questionListToQuestionAndAnswerDtoList(questionList);
    }
}
