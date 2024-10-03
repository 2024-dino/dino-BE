package khu.dino.event.implement;

import jakarta.transaction.Transactional;
import khu.dino.common.annotation.Adapter;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.Event;
import khu.dino.event.persistence.repository.EventRepository;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.growthObject.implement.GrowthObjectQueryAdapter;
import khu.dino.growthObject.persistence.GrowthObject;
import khu.dino.growthObject.persistence.repository.GrowthObjectRepository;
import khu.dino.question.persistence.Question;
import khu.dino.question.persistence.repository.QuestionRepository;
import khu.dino.question.presentation.dto.QuestionRequestDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Adapter
@RequiredArgsConstructor
public class EventCommandAdapter {
    private final EventRepository eventRepository;
    private final GrowthObjectRepository growthObjectRepository;
    private final QuestionRepository questionRepository;

    public Event save(Event event) {
        List<GrowthObject> growthObject = growthObjectRepository.findAllByStepAndEmotion(Step.LEVEL1, event.getEmotion());
        Random random = new Random();
        int randomIndex = random.nextInt(growthObject.size());

        event.setGrowthObject(growthObject.get(randomIndex));
        return eventRepository.save(event);
    }


    @Transactional
    public void update(Event event, EventRequestDto.modifyEventInfoDto request) {
        LocalDate startDate = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startDate, request.getEndDate());
        List<Question> notAnsweredQuestionList = questionRepository.findByEventAndIsAnsweredIsFalse(event);

        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < notAnsweredQuestionList.size(); i++) {
            int pos = (int) Math.round((double) i * totalDays / (notAnsweredQuestionList.size() - 1));
            if (!positions.contains(pos)) {
                positions.add(pos);
            }
        }
        List<LocalDate> pushDates = new ArrayList<>();
        for (int pos : positions) {
            LocalDate date = startDate.plusDays(pos);
            pushDates.add(date);
        }

        int i = 0;
        for(Question question : notAnsweredQuestionList){
            question.updateOccuredAt(pushDates.get(i));
            i++;
        }

        event.update(request);
    }

    public void deleteById(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
