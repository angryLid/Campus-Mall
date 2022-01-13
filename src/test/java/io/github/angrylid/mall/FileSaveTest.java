package io.github.angrylid.mall;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

@SpringBootTest
public class FileSaveTest {

    @Test
    void init() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint("http://119.91.147.80:9000/")
                    .credentials("angrylid", "angrylid").build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("mall").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("mall").build());
            } else {
                System.out.println("Bucket 'mall' already exists.");
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder().bucket("mall").object("demo.png")
                            .filename("C:\\Users\\mian\\Desktop\\assets\\Snipaste_2021-11-09_15-44-47.png").build());
            System.out.println("succeed");
        } catch (MinioException e) {
            System.out.println(e.httpTrace());
        }
    }
}
