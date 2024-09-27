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
    @Operation(summary = "새 이벤트 등록", description = "새로운 이벤트를 등록하는 API 입니다.")
    public CommonResponse<Long> createEvent(
            @AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
            @RequestBody final EventRequestDto.saveEventDto request) {
        return CommonResponse.onSuccess(eventService.saveNewEvent(principalDetails.getMember(), request));
    }


















    @Operation(summary="메인 특정 이벤트 불러오기", description = "메인 화면을 불러오는 API입니다. ")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/main")
    public CommonResponse<List<EventResponseDto.MainEvent>> getMainEvent(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) {
        return CommonResponse.onSuccess(eventService.getMainEvent(principalDetails));
    }

}
