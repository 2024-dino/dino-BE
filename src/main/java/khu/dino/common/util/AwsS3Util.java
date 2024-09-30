package khu.dino.common.util;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import khu.dino.answer.persistence.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Util {
    @Value("${cloud.aws.s3.bucket_name}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;


    public String uploadAnswerObject(String username, MultipartFile object,Long eventId, Long QuewsionId, Long AwnserId) throws Exception {


        String originalName = object.getOriginalFilename(); //파일 이름 추출
        String extension = Objects.requireNonNull(originalName).substring(originalName.lastIndexOf(".") + 1);
        String generateFileName = UUID.randomUUID() + "." + extension;
        log.info(generateFileName);


        String filename = "DayDream" + File.separator + username + File.separator + eventId + File.separator +  QuewsionId + File.separator +  AwnserId + File.separator + generateFileName;
        log.info(filename);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(object.getContentType() + ";charset=utf-8");
        objectMetadata.setContentEncoding("UTF-8");
        objectMetadata.setContentLength(object.getInputStream().available());

        amazonS3Client.putObject(bucket, filename, object.getInputStream(), objectMetadata);
        log.info(amazonS3Client.getUrl(bucket, filename).toString());
        return amazonS3Client.getUrl(bucket, filename).toString();


    }


    @Deprecated
    //Delete for Object
    public void deleteFile(String uploadFilePath) {
       if(amazonS3Client.doesObjectExist(bucket, uploadFilePath)){
           amazonS3Client.deleteObject(bucket, uploadFilePath);
       }else{
            log.info("File not found");
            //throw new Exception 구현 필요.
       }

    }
}
