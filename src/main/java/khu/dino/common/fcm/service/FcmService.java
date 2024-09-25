package khu.dino.common.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.fcm.FcmException;
import khu.dino.common.fcm.dto.FCMDto;
import khu.dino.member.business.MemberMapper;
import khu.dino.member.persistence.FcmToken;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.FcmTokenRepository;
import khu.dino.member.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class FcmService {
    Logger logger = LoggerFactory.getLogger(FcmService.class);

    private final FcmTokenRepository fcmTokenRepository;

    public void testFCMService(String fcmToken)
    {
        logger.info("받은 FCM 토큰 값 : " + fcmToken);
        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(
                        Notification.builder()
                                .setTitle("DayDream FCM 테스트")
                                .setBody("DayDream 메시지를 성공적으로 수신하였습니당")
                                .build())
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("the response of request FCM : {}",response);
        }catch (FirebaseMessagingException e){
            throw new FcmException(ErrCode.FCM_SEND_MESSAGE_ERROR.getMessage());
        }
    }

    @Transactional(readOnly = false)
    public MemberResponseDto.saveFcmToken saveFcmToken(Member member, FCMDto.saveFcmTokenDto request) {
        boolean isSuccess = true;
        logger.info("토큰 값 : {}", request.getFcmToken());


        // 한 member 당 가장 최신의 fcm token 값 하나만 db 에 저장하도록 수정
        if (fcmTokenRepository.existsByMember(member)) {
            try {
                FcmToken fcmToken = fcmTokenRepository.findByMember(member).orElseThrow(() -> new FcmException(ErrCode.FCM_TOKEN_NOT_EXISTS.getMessage()));
                fcmToken.update(request.getFcmToken());
            } catch (Exception e) {
                // exception 처리
            }
        } else {
            fcmTokenRepository.save(FcmToken.builder()
                    .member(member)
                    .token(request.getFcmToken())
                    .build()
            );
        }
        return MemberMapper.toSaveFcmToken(member, isSuccess);
    }


    public void sendFcmMessage(Member receiver, String title, String body) {

        FcmToken fcmToken = fcmTokenRepository.findByMember(receiver).orElseThrow(() -> new FcmException(ErrCode.FCM_TOKEN_NOT_EXISTS.getMessage()));

        String token = fcmToken.getToken();
        logger.info("전송하고자 하는 FCM 토큰 값 : " + token);

        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("the response of request FCM : {}",response);
        } catch (FirebaseMessagingException e) {
            logger.error("FCM 푸시 알림 전송 실패, 이유 : {}", e.getMessage());
            throw new FcmException(ErrCode.FCM_SEND_MESSAGE_ERROR.getMessage());
        }

    }

}
