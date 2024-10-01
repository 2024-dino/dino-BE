package khu.dino.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrCode {
    UNAUTHORIZED("D401", "인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED.value()),
    INVALID_ACCESS_TOKEN("D402", "JWT가 유효하지 않습니다.", HttpStatus.FORBIDDEN.value()),
    NO_FORBIDDEN("D403", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN.value()),
    DUPLICATED_EMAIL("D404", "이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST.value()),
    LOGIN_FAILED("D405", "아이디 또는 비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR("D500", "서버가 요청 처리에 실패하였습니다..", HttpStatus.INTERNAL_SERVER_ERROR.value()),



    FCM_ALREADY_EXISTS_TOKEN("FCM400_1", "이미 저장되어 있는 FCM 토큰입니다.",HttpStatus.BAD_REQUEST.value()),
    FCM_TOKEN_NOT_EXISTS("FCM400_2", "해당 유저의 FCM Token 이 존재하지 않습니다.",HttpStatus.BAD_REQUEST.value()),
    FCM_ACCESS_TOKEN_REQUEST_ERROR("FCM500_2", "서버 에러, FCM 서버에 AccessToken 요청할 때 에러 발생.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    FCM_SEND_MESSAGE_ERROR("FCM500_3", "서버 에러, FCM 서버에 메시지를 전송할 때 에러 발생. FcmToken이 유효한지 확인해주세요.", HttpStatus.INTERNAL_SERVER_ERROR.value()),

    EVENT_NOT_FOUND("EVENT404", "해당 event 를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()),

    MEMBER_NOT_FOUND("MEMBER404", "해당 member 를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()),

    QUESTION_NOT_FOUND("QUESTION404", "해당 질문을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()),

    ;

    private final String code;
    private final String message;
    private final int status;



}
