package khu.dino.common.util;

import khu.dino.answer.persistence.enums.Type;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


public class MultipartFileUtil {

    public static Type getFileType(MultipartFile file) {
        // 파일이 없거나 비어 있는 경우
        if (file == null || file.isEmpty()) {
            return Type.TEXT;
        }

        // MultipartFile의 데이터 타입(MIME 타입) 확인
        String contentType = file.getContentType();

        // 오디오 파일 (mp3, wav 등)
        if (Objects.requireNonNull(contentType).startsWith("audio")) {
            return Type.VOICE;
        }
        // 이미지 파일 (png, jpg, jpeg, gif 등)
        else if (contentType.startsWith("image")) {
            return Type.IMAGE;
        }
        else{
            return null;
        }

    }
}
