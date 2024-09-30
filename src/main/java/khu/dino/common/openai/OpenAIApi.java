package khu.dino.common.openai;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import khu.dino.common.scheduler.PushNotificationScheduler;
import khu.dino.common.openai.dto.OpenAIRequestDto;
import khu.dino.common.openai.dto.OpenAIResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Open API 목록", description = "Open관련 API 목록입니다.")
@RequestMapping("/api/v1/open-ai")
public class OpenAIApi {
    @Value("${openai.model}")
    private String openAIModel;

    private final OpenAIFeignClient openAIFeignClient;

    private final PushNotificationScheduler pushNotificationScheduler;


    /**
     * OpenAI API 테스트 용
     * 추후, 도메인 개발 완료 시 이벤트 생성 로직에 적용될 예정
     * @param eventTitle
     * @param emotion
     * @param discription
     * @param questionSize
     * @return
     * @throws java.text.ParseException
     */
    @Deprecated
    @Operation(summary="OpenAI API 테스트 용", description = "질문과 감정, 질뭇 갯수에 따른 생성일 발행 API")
    @GetMapping("/chat")
    public ResponseEntity<Map<Long, String>> chat(@RequestParam(name = "event-title")String eventTitle, @RequestParam("emotion") String emotion, @RequestParam(value = "discription", required = false)String discription,  @RequestParam(name = "question-size") Integer questionSize) throws java.text.ParseException, SchedulerException {
        OpenAIRequestDto.OpenAIRequestMessage systemMessage = OpenAIRequestDto.OpenAIRequestMessage.builder()
                .role("system")
                .content("너는 나에게 이 이벤트와 관련된 질문을 제공해주는 말동무이자 친구 역할이야. 초반에는 해당 일정에 대한 간단한 정보를 묻는 질문이 좋을 것 같고, " +
                        "후반에는 이 일정에 대한 나의 감정이나 생각을 묻는 질문이 좋을 것 같아. 내가 현재 느끼는 감정으 극대화 시킬 수 있는 단어들로 질문을 구성해 주었으면 좋겠어.")
                .build();
        String requestMessageBody = "내가 기대하고 있는 이벤트에 대한 제목은 다음과 같아." + eventTitle +
                "그리고 내가 현재 이 이벤트를 대하고 있는 마음가짐은 " + emotion +"이야. " +
                "내가 너로부터 이벤트에 대한 설레는 질문을 받을 갯수는 " + questionSize + "개야. " +
                (discription == null ? "" :  "해당 이벤트에 대한 부연 설명을 하자면 다음과 같아." +  discription ) +
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




        // 시작일과 종료일
        String startDateStr = "2024-09-01";
        String endDateStr = "2024-09-14";


        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        Map<Long, String> result = getStringStringMap(extractJsonData(openAIResponse.getChoices().get(0).getMessage().getContent()));


        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        float interval =  (float) totalDays / ( result.size() -1 );

        List<LocalDate> pushDates = new ArrayList<>();

        LocalDate currentPushDate = startDate.plusDays(1);  // 첫날은 제외하고 시작
        for (int i = 0; i < result.size()-1; i++) {
            pushDates.add(currentPushDate.plusDays(Math.round(interval * i)).isEqual(endDate) ? endDate.minusDays(1) : currentPushDate.plusDays(Math.round( interval * i))  ); //우선 담고 보자.
        }
        pushDates.add(endDate);
        for(int i = 0; i < result.size(); i++){
            log.info("질문 내용 : " + result.get((long) i + 1) + ", 질문 발생일: " + pushDates.get(i));
        }

        //비동기 적으로 실행됨
//        pushNotificationScheduler.schedulePushNotification(pushDates);

        return ResponseEntity.ok(result);

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
