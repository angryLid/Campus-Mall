package io.github.angrylid.mall.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class Minio {

    @Autowired
    static MinioClient minioClient;

    static final String IMAGE_TYPE = "image/";

    public static String upload(MultipartFile file) throws IOException {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("mall").object(filename)
                            .stream(file.getInputStream(), -1, 10485760)
                            .contentType(IMAGE_TYPE + extension).build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return filename;
    }
}
