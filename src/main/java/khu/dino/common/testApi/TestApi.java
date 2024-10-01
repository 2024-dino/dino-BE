package khu.dino.common.testApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import khu.dino.common.CommonResponse;
import khu.dino.common.fcm.dto.FCMDto;
import khu.dino.common.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러, 백앤드 개발자에게 알려주세요."),
})
@Tag(name = "API 호출/응답, jwt 등 테스트 관련 API 목록", description = "API 호출/응답, jwt 등 테스트 관련 API 목록입니다.")
public class TestApi {

    private final FcmService fcmService;

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy!";
    }


    @Operation(summary = "FCM 테스트 API", description = "테스트용")
    @PostMapping("/test/fcm")
    public CommonResponse<?> testFCM(@RequestBody FCMDto.FCMTestDto fcmToken) throws IOException
    {
        fcmService.testFCMService(fcmToken.getFcmToken());
        return CommonResponse.onSuccess("FCM 테스트 성공!");
    }

}
