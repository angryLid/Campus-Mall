package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.service.FavoriteService;

@ClientController("/favorite")
public class FavoriteClientApi {

    private FavoriteService favoriteService;

    @Autowired
    public FavoriteClientApi(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * 获取个人收藏的物品
     * 
     * @param userId    用户id
     * @param productId 物品id
     * @return
     */
    @GetMapping("/{productId}")
    public CustomResponse<?> isFavorite(@RequestAttribute("id") Integer userId,
            @PathVariable("productId") Integer productId) {
        Boolean result = favoriteService.isFavorite(userId, productId);

        return CustomResponse.success(result);
    }

    /**
     * 添加收藏
     * 
     * @param userId    用户id
     * @param productId 物品id
     * @return
     */

    @PostMapping("/{productId}")
    public CustomResponse<?> setFavorite(@RequestAttribute("id") Integer userId,
            @PathVariable("productId") Integer productId) {
        Integer result = favoriteService.insertFavorite(userId, productId);

        if (result == 1) {
            return CustomResponse.success("success");
        }
        return CustomResponse.dbException("fail");
    }

    /**
     * 取消收藏
     * 
     * @param userId
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public CustomResponse<?> deleteFavorite(@RequestAttribute("id") Integer userId,
            @PathVariable("productId") Integer productId) {
        Integer result = favoriteService.deleteFavorite(userId, productId);

        if (result == 1) {
            return CustomResponse.success("success");
        }
        return CustomResponse.dbException("fail");
    }

}
