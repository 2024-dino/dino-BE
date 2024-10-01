package khu.dino.common.openai;

import khu.dino.common.openai.dto.OpenAIRequestDto;
import khu.dino.common.openai.dto.OpenAIResponseDto;
import khu.dino.common.scheduler.PushNotificationScheduler;
import khu.dino.event.persistence.Event;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.member.persistence.Member;
import khu.dino.question.presentation.dto.QuestionRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIUtil {
    @Value("${openai.model}")
    private String openAIModel;

    private final OpenAIFeignClient openAIFeignClient;

    private final PushNotificationScheduler pushNotificationScheduler;


    public List<QuestionRequestDto.questionSimpleInfoDto> createQuestionList(EventRequestDto.saveEventDto request, Member member, Event event) throws SchedulerException {
        OpenAIRequestDto.OpenAIRequestMessage systemMessage = OpenAIRequestDto.OpenAIRequestMessage.builder()
                .role("system")
                .content("너는 나에게 이 이벤트와 관련된 질문을 제공해주는 말동무이자 친구 역할이야. 초반에는 해당 일정에 대한 간단한 정보를 묻는 질문이 좋을 것 같고, " +
                        "후반에는 이 일정에 대한 나의 감정이나 생각을 묻는 질문이 좋을 것 같아. 내가 현재 느끼는 감정으 극대화 시킬 수 있는 단어들로 질문을 구성해 주었으면 좋겠어.")
                .build();
        String requestMessageBody = "내가 기대하고 있는 이벤트에 대한 제목은 다음과 같아." + request.getTitle() +
                "그리고 내가 현재 이 이벤트를 대하고 있는 마음가짐은 " + request.getEmotion() +"이야. " +
                "내가 너로부터 이벤트에 대한 설레는 질문을 받을 갯수는 " + request.getQuestionSize() + "개야. " +
                (request.getMemo() == null ? "" :  "해당 이벤트에 대한 부연 설명을 하자면 다음과 같아." +  request.getMemo() ) +
                "무엇보다 중요한점은 서버가 받기 용이하게 질문을 JSON 형태로 가공해서 파싱하기 편하게 만들어 줬으면 좋겠어. " +
                "'questions'이라는 key값에 value는 배열 형태로 주되, 배열 내부 요소는 다시 각각 순서를 나타내는 id와 question이라는 key값으로 반환해 주었으면 해. ";
        OpenAIRequestDto.OpenAIRequestMessage userMessage = OpenAIRequestDto.OpenAIRequestMessage.builder()
                .role("user")
                .content(requestMessageBody)
                .build();
        OpenAIRequestDto.OpenAIRequest openAIRequest = OpenAIRequestDto.OpenAIRequest.builder()
                .model(openAIModel)
                .messages(List.of(systemMessage, userMessage))
                .build();


        OpenAIResponseDto.OpenAIResponse openAIResponse = openAIFeignClient.sendRequest(openAIRequest);


        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Map<Long, String> result = getStringStringMap(extractJsonData(openAIResponse.getChoices().get(0).getMessage().getContent()));


        long totalDays = ChronoUnit.DAYS.between(startDate.plusDays(1), endDate.minusDays(1));
        float interval =  (float) totalDays / ( result.size() - 2 ); // 이벤트 시작 날, 마지막 날 제외하여 인터벌 생성하도록.

        List<LocalDate> pushDates = new ArrayList<>();

        for (int i = 1; i < result.size()-1; i++) {
            pushDates.add(startDate.plusDays(Math.round(interval * i)).isEqual(endDate) ? endDate.minusDays(1) : startDate.plusDays(Math.round( interval * i))  ); //우선 담고 보자.
        }
        pushDates.add(endDate);
        for(int i = 0; i < result.size()-1; i++){
            log.info("질문 내용 : " + result.get((long) i + 1) + ", 질문 발생일: " + pushDates.get(i));
        }

        // 특정 이벤트에 대한 Question 리스트 생성하여 db에 저장하는 로직 추가하기
        List<QuestionRequestDto.questionSimpleInfoDto> questionSimpleInfoDtoList = new ArrayList<>();

        questionSimpleInfoDtoList.add(
                QuestionRequestDto.questionSimpleInfoDto.builder()
                        .content(result.get(1L)) //첫번째날 질문은 무조건 반환되어야하기에.
                        .occurredAt(request.getStartDate())
                        .build()
        );
        for(int i = 1; i < result.size(); i++){
            questionSimpleInfoDtoList.add(
            QuestionRequestDto.questionSimpleInfoDto.builder()
                    .content(result.get((long) i + 1))
                    .occurredAt(pushDates.get(i-1))
                    .build()
            );
        }


        //비동기 적으로 실행됨
        pushNotificationScheduler.schedulePushNotification(pushDates, request.getOccurrenceTime(), member.getId(), event.getId());

        return questionSimpleInfoDtoList;
    }

    private  Map<Long, String> getStringStringMap(String result) {
        JSONParser parser = new JSONParser();
        Map<Long, String> questionMap = new HashMap<>();
        try {

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONArray questionsArray = (JSONArray) jsonObject.get("questions");

            for (Object obj : questionsArray) {
                JSONObject questionObject = (JSONObject) obj;
                String question = (String) questionObject.get("question");
                Long id = (Long) questionObject.get("id");
                questionMap.put(id, question);

            }
        } catch (ParseException e) {
            throw new RuntimeException(e); //추후 에러 반환 처리 필요.
        }
        return questionMap;
    }


    private  String extractJsonData(String text) {

        int jsonStart = text.indexOf("```json") ;
        int jsonEnd = text.lastIndexOf("```");

        return text.substring(jsonStart, jsonEnd).replace("```json", "");
    }
}
