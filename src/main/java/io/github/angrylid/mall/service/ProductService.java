package io.github.angrylid.mall.service;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.mapper.MyProductMapper;
import io.github.angrylid.mall.utils.Minio;

@Service
public class ProductService {

    @Resource
    MyProductMapper myProductMapper;

    @Resource
    ProductMapper productMapper;

    @Autowired
    Minio minio;

    public void addProduct(PostProductDto dto) throws IllegalAccessException, IOException {
        var entity = new Product();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSellerId(10001);
        entity.setPrice(new BigDecimal(12.99));

        for (MultipartFile file = dto.getImage0(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage0(name);
        }
        for (MultipartFile file = dto.getImage1(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage1(name);
        }
        for (MultipartFile file = dto.getImage2(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage2(name);
        }
        for (MultipartFile file = dto.getImage3(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage3(name);
        }
        for (MultipartFile file = dto.getImage4(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage4(name);
        }
        for (MultipartFile file = dto.getImage5(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage5(name);
        }

        try {
            productMapper.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
