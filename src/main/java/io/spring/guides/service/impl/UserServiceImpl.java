package io.spring.guides.service.impl;

import com.github.pagehelper.PageHelper;
import io.spring.guides.jwt.JwtUtil;
import io.spring.guides.mbg.dao.UserMapper;
import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
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
        List<User> origin = mapper.selectAll();
        return origin;
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
        String token;

        User user = mapper.selectByPrimaryKey(primaryKey);

        if (user == null) {
            throw new IllegalArgumentException("User doesn't Exist!");
        } else if (!user.getPasswd().equals(password)) {
            throw new IllegalArgumentException("Wrong Password!");
        } else {
            token = JwtUtil.sign(user.getUname(), user.getUid().toString(), user.getPasswd());
        }

        return token;
    }

    @Override
    public boolean modifyUser(long primaryKey, Date separation, String department, String position, boolean isAdmin, String password) {
        User user = this.mapper.selectByPrimaryKey(primaryKey);
        user.setDateSeperation(separation);
        user.setDepartment(department);
        user.setPosition(position);
//        if(isAdmin != null){
//            user.setIsAdmin(isAdmin == true ? (byte)1:(byte)0);
//        }
//        user.setIsAdmin(isAdmin == true ? (byte)1:(byte)0);
        user.setPasswd(password);
        int result = this.mapper.updateByPrimaryKey(user);
        return result == 1;
    }

    @Override
    public boolean addUser(String name, String gender, Date entry, String department, String position, boolean isAdmin, String password) {
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
}
