package io.github.angrylid.mall.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.Swipe;
import io.github.angrylid.mall.service.SwipeService;

@ClientController("/swipe")
public class SwipeClientApi {

    private SwipeService swipeService;

    public SwipeClientApi(@Autowired SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    /**
     * 获取轮播图
     *
     * @return 轮播图
     */
    @GetMapping()
    public CustomResponse<?> getSwipes() {
        List<Swipe> swipes = swipeService.selectSwipes();
        return CustomResponse.success(swipes);
    }

}
