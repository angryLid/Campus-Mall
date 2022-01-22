package io.github.angrylid.mall.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.angrylid.mall.dto.EnrollmentStudent;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.entity.RoleType;
import io.github.angrylid.mall.entity.UnverifiedStudent;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.StudentMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.jwt.JwtUtil;
import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.mapper.UnverifiedStudentMapper;

@Service("userService")
public class UserService {

    @Resource
    private CustomUserMapper customUserMapper;

    @Resource
    UnverifiedStudentMapper unverifiedStudentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private StudentMapper studentMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String login(String telephone, String password) {
        User user = this.customUserMapper.getUser(telephone, password);
        if (user != null) {
            return JwtUtil.sign(telephone, password);
        }

        throw new IllegalArgumentException("Wrong tel or password");
    }

    public String addUser(String telephone, String password, String nickname) {
        User user = new User();
        user.setTelephone(telephone);
        user.setPasswd(password);
        user.setNickname(nickname);
        user.setRoleType(RoleType.UNKNOWN.getStatus());
        try {
            userMapper.insert(user);
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
            friend.setName(user.getNickname());
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

    public List<UnverifiedStudent> getUnverifiedStudents() {
        return unverifiedStudentMapper.getStudents();
    }

    /**
     * @param uid user table's id column
     * @return Infected row, value is 1 unless error occurs
     */
    public Integer permitStudent(Integer uid) {
        User user = new User();
        user.setId(uid);
        user.setRoleType("student_verified");
        return userMapper.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void enrollBatch(EnrollmentStudent input) {

        User user = new User();
        user.setTelephone(input.getTelephone());
        user.setPasswd("12345678");
        user.setRoleType(RoleType.STUDENT_VERIFIED.getStatus());
        user.setNickname(input.getName() + input.getTelephone().substring(9, 11));
        userMapper.insert(user);

        Student student = new Student();
        student.setName(input.getName());
        student.setBelongTo(input.getBelongTo());
        student.setStudentId(input.getStudentId());
        student.setRelatedUser(user.getId());
        studentMapper.insert(student);

        logger.error("{}", student);
    }

    public String getUserStatus(Integer id) {
        User user = userMapper.selectById(id);
        return user.getRoleType();
    }
}
