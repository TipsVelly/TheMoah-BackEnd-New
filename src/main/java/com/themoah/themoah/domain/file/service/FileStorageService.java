package com.themoah.themoah.domain.file.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.themoah.themoah.common.config.aws.s3.S3Config;
import com.themoah.themoah.common.util.s3.S3Util;
import com.themoah.themoah.domain.file.entity.FileStorage;
import com.themoah.themoah.domain.file.repository.FileStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.net.URL;
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final AmazonS3 amazonS3;
    private final S3Config s3Config;
    private final FileStorageRepository fileStorageRepository;

    @Transactional
    public String fileUpload(MultipartFile file, String fileGroup, String groupId) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        String fileName = generateFileName(file);

        String bucketName = s3Config.getBucketName();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        String s3FileUrl = amazonS3.getUrl(bucketName, fileName).toString();


        // 파일 정보를 FileStorage에 저장
        FileStorage fileStorage = FileStorage.builder()
                .s3FileUrl(s3FileUrl)
                .fileGroup(fileGroup)
                .groupId(groupId)
                .fileName(fileName)
                .build();
        fileStorageRepository.save(fileStorage);

        return s3FileUrl;
    }

    private String generateFileName(MultipartFile file) {

        // 파일 확장자 추출
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // UUID를 생성하여 파일 이름으로 사용
        String uuidFileName = UUID.randomUUID().toString();

        // 최종 파일 이름 생성 (UUID + 파일 확장자)
        return uuidFileName + extension;

    }

    public String findFileUrlByGroupAndGroupId(String fileGroup, String groupId) {
        Optional<FileStorage> fileStorageOptional = fileStorageRepository.findByFileGroupAndGroupId(fileGroup, groupId);

        if (fileStorageOptional.isPresent()) {
            FileStorage fileStorage = fileStorageOptional.get();
            S3Util s3Util = new S3Util(amazonS3, s3Config.getBucketName());
            URL url = s3Util.generatePreSignedURL(fileStorage.getFileName(),60);
            return url.toString();
        } else {
            throw new RuntimeException("File not found for the given group and group ID");
        }
    }

}