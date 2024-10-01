package khu.dino.question.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import khu.dino.answer.presentation.dto.AnswerResponseDto;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.business.EventService;
import khu.dino.question.business.QuestionService;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러, 백앤드 개발자에게 알려주세요."),
})
@Tag(name = "Question 관련 API 목록", description = "Question 관련 API 목록입니다.")
@RequestMapping("/api/v1/question")
public class QuestionApi {

    private final QuestionService questionService;
    private final EventService eventService;



    @Operation(summary = "Hi-Story에서의 저장한 질문 내역 반환", description = "Hi-Story에서의 저장한 질문 리스트를 반환하는 API입니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hi-story")
    public CommonResponse<List<QuestionResponseDto.PriorityQuestion>> getHiStoryQuestion(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) {
        return CommonResponse.onSuccess(questionService.getHiStoryQuestion(principalDetails));
    }

    @Operation(summary="[대표 질문 선택 페이지] 특정 이벤트의 질문/답변 목록 조회 API", description = "특정 이벤트의 질문/답변 목록을 조회하는 API 입니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{eventId}")
    public CommonResponse<List<AnswerResponseDto.questionAndAnswerDto>> getQuestionAndAnswerList(
            @AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
            @PathVariable(name = "eventId") Long eventId){
            return CommonResponse.onSuccess(questionService.getQuestionAndAnswerList(principalDetails, eventId));
    }

    @Operation(summary="[대표 질문 선택 페이지] 대표 질문 선택 API", description = "특정 이벤트의 대표 질문을 선택하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "QUESTION400_2", description = "BAD_REQUEST, 해당 질문은 해당 이벤트의 질문이 아닙니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{event-id}/representativeQuestion/{question-id}")
    public CommonResponse<?> setRepresentativeQuestion(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
                                                          @RequestParam(required = true, name= "event-id") Long eventId,
                                                          @RequestParam(required = true, name= "question-id") Long questionId){
        eventService.setRepresentativeQuestion(eventId, questionId);
        return CommonResponse.onSuccess(null);
    }


    @Operation(summary = "질문 북마크 선택 및 취소하기", description = "질문을 저장하는(북마크)하거나 취소하는 API입니다.")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{question-id}/priority")
    public CommonResponse<Void> setQuestionBookmarkStatus(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails, @RequestParam(required = true, name="question-id") Long questionId, @RequestParam(required = true, name="priority") Boolean priority) {
        return  CommonResponse.onSuccess(questionService.setQuestionPriorityStatus(principalDetails, questionId, priority));
    }

}
