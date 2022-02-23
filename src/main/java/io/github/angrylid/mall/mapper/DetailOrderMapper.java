package io.github.angrylid.mall.mapper;

import java.util.List;

import io.github.angrylid.mall.entity.DetailOrder;

public interface DetailOrderMapper {
    List<DetailOrder> selectSold(Integer userId);

    List<DetailOrder> selectBought(Integer userId);
}
