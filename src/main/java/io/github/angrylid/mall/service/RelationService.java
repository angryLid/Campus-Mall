package io.github.angrylid.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Relation;
import io.github.angrylid.mall.generated.mapper.RelationMapper;

@Service
public class RelationService {

    private RelationMapper relationMapper;

    @Autowired
    public RelationService(RelationMapper relationMapper) {
        this.relationMapper = relationMapper;
    }

    /**
     * 关注
     * 
     * @param userId
     * @param targetId
     * @return
     * 
     */
    public Integer follow(Integer userId, Integer followerId) {
        Relation relation = new Relation();
        relation.setUserId(userId);
        relation.setFollowerId(followerId);
        return relationMapper.insert(relation);
    }

    /**
     * 取消关注
     * 
     * @param userId
     * @param followerId
     * @return
     */
    public Integer unfollow(Integer userId, Integer followerId) {
        return relationMapper.delete(new QueryWrapper<Relation>().eq("user_id", userId).eq("follower_id", followerId));
    }

    /**
     * 查询关注
     * 
     * @param userId
     * @return
     */
    public Boolean isFollowing(Integer userId, Integer followerId) {
        return relationMapper
                .selectOne(new QueryWrapper<Relation>().eq("user_id", userId).eq("follower_id", followerId)) != null;
    }

}
