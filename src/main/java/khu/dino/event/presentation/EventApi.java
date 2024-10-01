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
import khu.dino.event.presentation.dto.EventRequestDto;
import khu.dino.event.presentation.dto.EventResponseDto;
import khu.dino.member.persistence.Member;
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

    @Operation(summary="특정 이벤트 상세 조회 API", description = "특정 이벤트 상세 조회 API 입니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{eventId}")
    public CommonResponse<EventResponseDto.eventDetailDto> getMainEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
                                                                         @PathVariable(name = "eventId") Long eventId) {

        return CommonResponse.onSuccess(eventService.getEventDetailInfo(principalDetails.getMember(), eventId));
    }

















        @Operation(summary="메인 특정 이벤트 불러오기", description = "메인 화면을 불러오는 API입니다. ")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/main")
    public CommonResponse<List<EventResponseDto.MainEvent>> getMainEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) {
        return CommonResponse.onSuccess(eventService.getMainEvent(principalDetails));
    }

}
