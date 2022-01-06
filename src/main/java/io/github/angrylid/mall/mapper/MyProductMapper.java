package io.github.angrylid.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyProductMapper {

    int addProduct(String title, String description, int price);
}
