package khu.dino.exception;

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
    INTERNAL_SERVER_ERROR("D500", "서버가 요청 처리에 실패하였습니다..", HttpStatus.INTERNAL_SERVER_ERROR.value());




    private final String code;
    private final String message;
    private final int status;



}
