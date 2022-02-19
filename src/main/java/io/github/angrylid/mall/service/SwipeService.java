package io.github.angrylid.mall.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.UploadSwipeDTO;
import io.github.angrylid.mall.generated.entity.Swipe;
import io.github.angrylid.mall.generated.mapper.SwipeMapper;
import io.github.angrylid.mall.utils.Minio;

/**
 * Swipe - 轮播图
 */
@Service
public class SwipeService {

    private SwipeMapper swipeMapper;

    private Minio minio;

    public SwipeService(@Autowired SwipeMapper swipeMapper, @Autowired Minio minio) {
        this.swipeMapper = swipeMapper;
        this.minio = minio;
    }

    /**
     * 获取轮播图列表（上架）
     *
     * @return 轮播图列表
     * 
     */
    public List<Swipe> selectSwipes() {
        return swipeMapper.selectList(new QueryWrapper<Swipe>().eq("status", 1));
    }

    /**
     * 获取轮播图列表（全部）
     *
     * @return 轮播图列表
     * 
     */
    public List<Swipe> selectAllSwipes() {
        return swipeMapper.selectList(null);
    }

    /**
     * 更新轮播图状态(下架)
     * 
     * @param id
     * @return
     */
    public Integer updateSwipeStatus(Integer id) {
        Swipe swipe = swipeMapper.selectById(id);
        swipe.setStatus(swipe.getStatus() == 1 ? 0 : 1);
        return swipeMapper.updateById(swipe);
    }

    /**
     * 新增一个活动
     * 
     * @param swipeDTO
     * @return
     * @throws IOException 上传图片失败
     */
    public Integer insertSwipe(UploadSwipeDTO swipeDTO) throws IOException {
        Swipe swipe = new Swipe();
        swipe.setTitle(swipeDTO.getTitle());
        swipe.setDetail(swipeDTO.getDetail());
        swipe.setStatus(0);
        String uuid = minio.upload(swipeDTO.getImage());
        swipe.setImage(uuid);
        return swipeMapper.insert(swipe);
    }
}
