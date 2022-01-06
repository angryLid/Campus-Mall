package io.github.angrylid.mall.service;

import io.github.angrylid.mall.mapper.MyProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductService {

    @Resource
    MyProductMapper myProductMapper;

    public String addProduct(String title, String description, String[] images,int price){

        int insertedId;
        try {
           insertedId =  this.myProductMapper.addProduct(title,description,price);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return "True";
    }
}
