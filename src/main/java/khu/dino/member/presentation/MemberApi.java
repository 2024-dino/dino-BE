package khu.dino.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.member.business.MemberService;
import khu.dino.member.presentation.dto.MemberRequestDto;
import khu.dino.member.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러, 백앤드 개발자에게 알려주세요."),
})
@Tag(name = "Member 관련 API 목록", description = "Member 관련 API 목록입니다.")
@RequestMapping("/api/v1/auth")
public class MemberApi {

    private final MemberService memberService;

    @Operation(summary="JWT 유효성 확인 및 기본 유저 정보 반환", description = "JWT 유효성 확인 및 기본 유저 정보 반환")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/validation-jwt")
    public CommonResponse<MemberResponseDto.MemberResponse> validateJwt(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails) throws ServletException, IOException {
        return CommonResponse.onSuccess(memberService.validateJwt(principalDetails));
    }

    @Operation(summary="사용자 이름 변경", description = "기존 사용자의 이름을 변경하는 API입니다.")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/nickname")
    public CommonResponse<MemberResponseDto.MemberResponse> changeNickname(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,@RequestBody MemberRequestDto.MemberRequest request) {

        return CommonResponse.onSuccess(memberService.changeNickname(principalDetails, request));

    }
}
