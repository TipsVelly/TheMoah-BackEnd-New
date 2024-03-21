package com.themoah.themoah.common.util.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;

public class S3Util {
    private AmazonS3 s3Client;
    private String bucketName;

    // AmazonS3 클라이언트와 버킷 이름을 받는 생성자
    public S3Util(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    // S3에 저장된 파일에 대한 프리사인드 URL을 생성하는 메서드
    public URL generatePreSignedURL(String objectKey, int expirationInMinutes) {
        // 만료 시간 설정
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * expirationInMinutes;
        expiration.setTime(expTimeMillis);

        // 프리사인드 URL 생성 요청
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        // 프리사인드 URL 생성
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url;
    }
}