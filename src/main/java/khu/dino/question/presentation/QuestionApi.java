package khu.dino.question.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.question.business.QuestionService;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResponse<List<QuestionResponseDto.CalendarEvent>> getCalendarEvent(@AuthMember PrincipalDetails principalDetails, @RequestParam(required = true, name = "date") String requestMonth) {
        return CommonResponse.onSuccess(questionService.getCalendarEvent(principalDetails, requestMonth));
    }

}
