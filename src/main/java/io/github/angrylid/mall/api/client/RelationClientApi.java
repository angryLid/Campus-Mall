package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.RelationService;

@ClientController("/relation")
public class RelationClientApi {

    private RelationService relationService;

    @Autowired
    public RelationClientApi(RelationService relationService) {
        this.relationService = relationService;
    }

    @TokenRequired
    @PostMapping("/{followerId}")
    public CustomResponse<?> follow(@RequestAttribute("id") Integer userId,
            @PathVariable("followerId") Integer followerId) {

        Integer result = relationService.follow(userId, followerId);
        if (result == 1) {
            return CustomResponse.success("关注成功");
        }
        return CustomResponse.dbException("关注失败");
    }

    @TokenRequired
    @DeleteMapping("/{followerId}")
    public CustomResponse<?> unfollow(@RequestAttribute("id") Integer userId,
            @PathVariable("followerId") Integer followerId) {

        Integer result = relationService.unfollow(userId, followerId);
        if (result == 1) {
            return CustomResponse.success("取消关注成功");
        }
        return CustomResponse.dbException("取消关注失败");
    }

    @TokenRequired
    @GetMapping("/{followerId}")
    public CustomResponse<?> isFollowing(@RequestAttribute("id") Integer userId,
            @PathVariable("followerId") Integer followerId) {

        Boolean result = relationService.isFollowing(userId, followerId);
        if (result) {
            return CustomResponse.success(true);
        }
        return CustomResponse.success(false);
    }
}
