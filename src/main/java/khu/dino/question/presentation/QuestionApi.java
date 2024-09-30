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

    @Operation(summary="캘린더 불러오기", description = "특정 년-월에 해당하는 각 날짜 별 이벤트 내역을 반환")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calendar")
    public CommonResponse<List<QuestionResponseDto.CalendarEvent>> getCalendarEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails, @RequestParam(required = true, name = "date") String requestMonth) {
        return CommonResponse.onSuccess(questionService.getCalendarEvent(principalDetails, requestMonth));
    }

    @Operation(summary="[대표 질문 선택 페이지] 특정 이벤트의 질문/답변 목록 조회 API", description = "특정 이벤트의 질문/답변 목록을 조회하는 API 입니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{eventId}")
    public CommonResponse<List<AnswerResponseDto.questionAndAnswerDto>> getQuestionAndAnswerList(
            @AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
            @PathVariable(name = "eventId") Long eventId){
            return CommonResponse.onSuccess(questionService.getQuestionAndAnswerList(principalDetails, eventId));
    }

}
