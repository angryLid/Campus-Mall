package io.github.angrylid.mall.api.admin;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.UploadSwipeDTO;
import io.github.angrylid.mall.generated.entity.Swipe;
import io.github.angrylid.mall.service.SwipeService;

@AdminController("/swipe")
public class SwipeApi {

    private static final Logger logger = LoggerFactory.getLogger(SwipeApi.class);

    private SwipeService swipeService;

    public SwipeApi(@Autowired SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    /**
     * 获取轮播图列表（所有）
     * GET /admin/swipe
     * 
     * @return
     */
    @GetMapping
    public CustomResponse<?> getSwipeList() {
        List<Swipe> swipeList = swipeService.selectAllSwipes();
        return CustomResponse.success(swipeList);
    }

    /**
     * 更新轮播状态
     * PUT /admin/swipe/{id}
     * 
     * @param id 轮播图id
     * @return
     */
    @PutMapping("/{id}")
    public CustomResponse<?> putSwipeStatus(@PathVariable("id") Integer id) {
        Integer result = swipeService.updateSwipeStatus(id);
        if (result == 1) {
            return CustomResponse.success("更新成功");
        }
        return CustomResponse.dbException("更新失败");
    }

    /**
     * 发布一个活动
     * POST /admin/swipe
     * 
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<?> postSwipe(@ModelAttribute UploadSwipeDTO uploadSwipeDTO) {
        logger.warn("Upload:{}", uploadSwipeDTO);
        try {
            Integer result = swipeService.insertSwipe(uploadSwipeDTO);
            if (result == 1) {
                return CustomResponse.success("发布成功");
            } else {
                return CustomResponse.dbException("发布失败");
            }

        } catch (IOException e) {
            logger.error("Upload Swipe Error", e);
            return CustomResponse.dbException("上传失败");
        }
    }
}
