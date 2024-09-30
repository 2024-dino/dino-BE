package khu.dino.event.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.event.business.EventService;
import khu.dino.event.persistence.enums.Status;
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.question.business.QuestionService;
import khu.dino.question.presentation.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러, 백앤드 개발자에게 알려주세요."),
})
@Slf4j
@Tag(name = "Event 관련 API 목록", description = "Event 관련 API 목록입니다.")
@RequestMapping("/api/v1/event")
public class EventApi {
    private final EventService eventService;
    private final QuestionService questionService;



    @PostMapping("/")
    @Operation(summary = "새로운 이벤트 생성하기", description = "새로운 이벤트를 생성하는 API 입니다.")
    public CommonResponse<?> createEvent (
            @AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
            @RequestBody final EventRequestDto.saveEventDto request) throws Exception{

        eventService.saveNewEvent(principalDetails.getMember(), request);

        return CommonResponse.onSuccess(null);
    }


    @Operation(summary="이벤트 일정 수정하기", description = "이벤트 일정을 수정하는 API 입니다.")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{eventId}")
    public CommonResponse<List<EventResponseDto.MainEvent>> modifyEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
        @PathVariable(name = "eventId") Long eventId,
        @RequestBody EventRequestDto.modifyEventInfoDto request) {
        eventService.modifyEventInfo(principalDetails.getMember(), eventId, request);
        return CommonResponse.onSuccess(null);
    }













    @Operation(summary="캘린더 불러오기", description = "특정 년-월에 해당하는 각 날짜 별 이벤트 내역을 반환")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calendar")
    public CommonResponse<List<QuestionResponseDto.CalendarEvent>> getCalendarEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails, @RequestParam(required = true, name = "date") String requestMonth) {
        return CommonResponse.onSuccess(questionService.getCalendarEvent(principalDetails, requestMonth));
    }


    @Operation(summary="메인 특정 이벤트 불러오기", description = "메인 화면을 불러오는 API입니다. ")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/main")
    public CommonResponse<List<EventResponseDto.MainEvent>> getMainEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) {
        return CommonResponse.onSuccess(eventService.getMainEvent(principalDetails));
    }






    @Operation(summary = "완성한/진행중 내역 반환", description = "완료/진행중인 이벤트 내역을 반환하는 API입니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public CommonResponse<List<EventResponseDto.EventInfo>> getEvents(@RequestParam(name ="event-status") String eventStatus, @AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) {
        return CommonResponse.onSuccess(eventService.getEvents(eventStatus, principalDetails.getMember()));
    }




}
