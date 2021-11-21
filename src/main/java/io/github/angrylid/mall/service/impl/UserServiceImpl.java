package io.github.angrylid.mall.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.angrylid.mall.jwt.JwtUtil;
import io.github.angrylid.mall.mbg.dao.UserMapper;
import io.github.angrylid.mall.mbg.entity.User;
import io.github.angrylid.mall.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Override
    public User queryUserById(long id) {
        return this.mapper.selectByPrimaryKey(id);
//        return new User(233L,"USERL","F");
    }

    @Override
    public List<User> fetchAllUsers() {
        return this.mapper.selectAll();
    }

    @Override
    public List<User> fetchUsers() {
        PageHelper.startPage(1, 3);
        return mapper.selectAll();

    }

    @Override
    public String login(String name, String password) {
        String token = "null";
        try {
            User user = mapper.selectByPrimaryKey(1L);
            if (user == null) {
                return "401";
            } else {
                if (!user.getPasswd().equals(password)) {
                    return "401";
                } else {
                    token = JwtUtil.sign(user.getUname(), user.getUid().toString(), user.getPasswd());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public String login(long primaryKey, String password) throws IllegalArgumentException {

        User user = mapper.selectByPrimaryKey(primaryKey);

        if (user == null) {
            throw new IllegalArgumentException("User doesn't Exist!");
        }

        if (user.getDateSeperation() != null) {
            throw new IllegalArgumentException("User has left the company!");
        }

        if (!user.getPasswd().equals(password)) {
            throw new IllegalArgumentException("Wrong Password!");
        }
        // Validation passed.
        return JwtUtil.sign(user.getUid(), user.getIsAdmin());
    }

    @Override
    public boolean modifyUser(long primaryKey, String department, String position,
                              boolean isAdmin, String password) {
        User user = this.mapper.selectByPrimaryKey(primaryKey);
        if (user == null) {
            return false;
        }


        user.setDepartment(department);
        user.setPosition(position);
        user.setIsAdmin(isAdmin ? (byte) 1 : (byte) 0);
        user.setPasswd(password);

        int result = this.mapper.updateByPrimaryKey(user);
        return result == 1;
    }

    @Override
    public boolean addUser(String name, String gender, Date entry, String department, String position,
                           boolean isAdmin, String password) {
        User user = new User();
        user.setUname(name);
        user.setUsex(gender);
        user.setDateEntry(entry);
        user.setPosition(position);
        user.setIsAdmin(isAdmin ? (byte) 1 : (byte) 0);
        user.setPasswd(password);
        user.setDepartment(department);

        int result = this.mapper.insert(user);
        return result == 1;

    }

    @Override
    public List<User> fetchUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectAll();
    }

    @Override
    public boolean fireUser(Long id) {
        User user = this.mapper.selectByPrimaryKey(id);
        user.setDateSeperation(new Date());
        int result = this.mapper.updateByPrimaryKey(user);
        return result == 1;
    }
}
