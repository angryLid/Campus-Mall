package io.github.angrylid.mall.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.jwt.JwtUtil;
import io.github.angrylid.mall.mapper.CustomUserMapper;

@Service("userService")
public class UserService {

    @Resource
    private CustomUserMapper customUserMapper;

    public String login(String telephone, String password) {
        User user = this.customUserMapper.getUser(telephone, password);
        if (user != null) {
            return JwtUtil.sign(telephone, password);
        }

        throw new IllegalArgumentException("Wrong tel or password");
    }

    public String addUser(String telephone, String password, String nickname) {
        try {
            this.customUserMapper.addUser(telephone, password, nickname);
        } catch (Exception e) {
            throw e;
        }
        return "Succeed!";
    }

    public User getUserByTel(String telephone) {
        User user = new User();
        try {
            user = this.customUserMapper.getUserByTel(telephone);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    public AccountInformation getFollowingAndFollowedOfCurrentUser(int id) {
        AccountInformation friend = new AccountInformation();
        try {
            friend.setFollowing(this.customUserMapper.getFollowingSpecificUser(id));
            friend.setFollowed(this.customUserMapper.getFollowedSpecificUser(id));

            User user = this.customUserMapper.getUserById(id);
            friend.setName(user.getNikename());
            friend.setTelephone(user.getTelephone());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return friend;
    }

    public boolean verifyJwt(String token) {
        return JwtUtil.verify(token);
    }
}
