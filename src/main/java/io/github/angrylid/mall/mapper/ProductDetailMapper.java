package io.github.angrylid.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.github.angrylid.mall.dto.response.ProductDetail;

@Mapper
public interface ProductDetailMapper {
    public List<ProductDetail> selectList();

    public ProductDetail selectOneById(@Param("id") Integer id);
}
