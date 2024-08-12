package khu.dino.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.member.business.MemberService;
import khu.dino.member.presentation.dto.MemberReseponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Member 관련 API 목록", description = "Member 관련 API 목록입니다.")
@RequestMapping("/api/v1/member")
public class MemberApi {

    private final MemberService memberService;

    @Operation(summary="JWT 유효성 확인 및 기본 유저 정보 반환", description = "JWT 유효성 확인 및 기본 유저 정보 반환")
    @GetMapping("/auth/validation-jwt")
    public CommonResponse<MemberReseponseDto.MemberResponse> validateJwt(@AuthMember PrincipalDetails principalDetails) throws ServletException, IOException {
       log.info(principalDetails.toString());
        return CommonResponse.onSuccess(memberService.validateJwt(principalDetails));
    }

}
