package khu.dino.common.util;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Util {
    @Value("${cloud.aws.s3.bucket_name}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;


    public String uploadGrowthObject(Long userId, ByteArrayOutputStream byteArrayOutputStream) throws Exception {
        //추후 확장자 명 변경 필요
        String generateFileName = UUID.randomUUID().toString().substring(0,8) + ".png";
        String filename = userId + File.separator +  generateFileName;

        byte[] qrCodeBytes = byteArrayOutputStream.toByteArray();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/png" + ";charset=utf-8");
        objectMetadata.setContentEncoding("UTF-8");
        objectMetadata.setContentLength(qrCodeBytes.length);
        InputStream inputStream = new ByteArrayInputStream(qrCodeBytes);
        amazonS3Client.putObject(bucket, filename, inputStream, objectMetadata);
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
