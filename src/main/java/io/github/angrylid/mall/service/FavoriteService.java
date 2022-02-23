package io.github.angrylid.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Favorite;
import io.github.angrylid.mall.generated.mapper.FavoriteMapper;

@Service
public class FavoriteService {

    private FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteService(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    public Boolean isFavorite(Integer userId, Integer productId) {
        Favorite favorite = favoriteMapper
                .selectOne(new QueryWrapper<Favorite>().eq("user_id", userId).eq("product_id", productId));
        return favorite != null;
    }

    public Integer insertFavorite(Integer userId, Integer productId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        return favoriteMapper.insert(favorite);
    }

    public Integer deleteFavorite(Integer userId, Integer productId) {
        return favoriteMapper.delete(new QueryWrapper<Favorite>().eq("user_id", userId).eq("product_id", productId));
    }

    public Long selectSum(Integer userId) {
        return favoriteMapper.selectCount(new QueryWrapper<Favorite>().eq("user_id", userId));
    }

}
