package khu.dino.answer.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import khu.dino.answer.business.AnswerService;
import khu.dino.answer.presentation.dto.AnswerResponseDto;
import khu.dino.common.CommonResponse;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러, 백앤드 개발자에게 알려주세요."),
})
@Tag(name = "Answer 관련 API 목록", description = "Answer 관련 API 목록입니다.")
@RequestMapping("/api/v1/answer")
public class AnswerApi {
    private final AnswerService answerService;

//    @Operation(summary="질문에 대한 답변 작성하기 API", description = "특정 질문에 대한 답변을 작성하는 API 입니다.")
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/{questionId}")
//    public CommonResponse<?> createAnswer(@AuthMember @Parameter(hidden = true) PrincipalDetails principalDetails,
//
//                                          ){
//
//        return CommonResponse.onSuccess(null);
//    }
    private final AwsS3Util awsS3Util;


    @Operation(summary = "S3 버킷 내 파일 업로드 기능입니다, ", description = "S3 업로드를 담당하는 테스트 용 API입니다.")
    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String uploadS3Object(@AuthMember PrincipalDetails principalDetails, @RequestPart(value = "mediaFile", required= true) MultipartFile multipartFile) throws Exception {
        return awsS3Util.uploadAnswerObject(principalDetails.getMember().getSocialId(), multipartFile, 0L, 0L, 0L );
    }


}
