package io.github.angrylid.mall.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.github.angrylid.mall.mapper.MyProductMapper;

@Service
public class ProductService {

    @Resource
    MyProductMapper myProductMapper;

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
}
