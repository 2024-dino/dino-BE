package khu.dino.question.business;

import khu.dino.answer.presentation.dto.AnswerResponseDto;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import khu.dino.common.CommonResponse;
import khu.dino.event.business.EventMapper;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.question.implement.QuestionCommandAdapter;
import khu.dino.question.implement.QuestionQueryAdapter;
import khu.dino.question.persistence.Question;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionQueryAdapter questionQueryAdapter;
    private final QuestionCommandAdapter questionCommandAdapter;

    private final QuestionMapper questionMapper;
    private final EventQueryAdapter eventQueryAdapter;
    private final EventMapper eventMapper;

    public List<QuestionResponseDto.CalendarEvent> getCalendarEvent(PrincipalDetails principalDetails, String requestMonth) {
        YearMonth yearMonth = YearMonth.parse(requestMonth);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();


        List<Question> questions = questionQueryAdapter.occurredAtBetweenStartDateAndEndDate(principalDetails, startDate, endDate);

        Map<LocalDate, List<EventResponseDto.EventContent>> groupedQuestions = questions.stream()
                .collect(Collectors.groupingBy(
                        Question::getOccurredAt,
                        Collectors.mapping(eventMapper::toEventContent,
                            Collectors.toList())
                ));

        return groupedQuestions.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) //오름차순
//                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) //내림차순
                .map(entry -> QuestionResponseDto.CalendarEvent.builder()

                .eventDate(entry.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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

    public List<QuestionResponseDto.PriorityQuestion> getHiStoryQuestion(PrincipalDetails principalDetails) {

        List<Question> questions = questionQueryAdapter.getHiStoryQuestion(principalDetails);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, List<QuestionResponseDto.QuestionContent>> groupedQuestions = questions.stream()
                .collect(Collectors.groupingBy(
                        question -> question.getOccurredAt().format(formatter),
                        Collectors.mapping(questionMapper::toQuestionContent, Collectors.toList())
                ));
        return groupedQuestions.entrySet().stream()
                .map(entry -> QuestionResponseDto.PriorityQuestion.builder()
                        .groupByDate(entry.getKey())
                        .questionContent(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public Void setQuestionPriorityStatus(PrincipalDetails principalDetails, Long questionId, Boolean priority) {
        Question question = questionQueryAdapter.getQuestionByIdAndOwner(questionId, principalDetails.getMember());
        questionCommandAdapter.setQuestionBookmarkStatus(question, priority);
        return null;
    }
}
