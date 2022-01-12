package io.github.angrylid.mall.service;

import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.mapper.MyProductMapper;
import io.github.angrylid.mall.utils.Minio;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProductService {

    private final Path root = Paths.get("C:/Users/mian/Desktop/assets");
    @Resource
    MyProductMapper myProductMapper;

    @Autowired
    MinioClient minioClient;

    public String addProduct(String title, String description, String[] images, int price) {

        int insertedId;
        try {
            insertedId = this.myProductMapper.addProduct(title, description, price);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "True";
    }

    public void addProduct(MultipartFile file) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        final String IMAGE_TYPE = "image/";
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString();
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket("mall").object(filename)
                    .stream(file.getInputStream(), -1, 10485760)
                    .contentType(IMAGE_TYPE + extension).build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void addProduct(PostProductDto postProductDto) throws IllegalAccessException, IOException {
        Field[] fields = postProductDto.getClass().getDeclaredFields();
        for (Field f : fields) {
            String filedName = f.getName();
            if (filedName.startsWith("image")) {
                f.setAccessible(true);
                MultipartFile image = (MultipartFile) f.get(postProductDto);
                if (image != null) {
                    String name = Minio.upload(image);
                    System.out.println(filedName + " " + name);
                }
            }
        }
    }
}
