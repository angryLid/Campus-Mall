package io.github.angrylid.mall.service;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.EnrollmentStudent;
import io.github.angrylid.mall.dto.EnterpriseQualification;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.entity.RoleType;
import io.github.angrylid.mall.entity.UnverifiedStudent;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;
import io.github.angrylid.mall.generated.mapper.StudentMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.jwt.JwtUtil;
import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.mapper.UnverifiedStudentMapper;
import io.github.angrylid.mall.utils.Minio;

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

    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    Minio minio;

    /**
     * 提交资质认证工单
     * 
     * @param dto       接受的表单数据
     * @param applicant 解码token的申请人id
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void qualificationRequset(EnterpriseQualification dto, Integer applicant)
            throws IllegalAccessException, IOException {
        var entity = new Qualification();
        entity.setEnterpriseName(dto.getEnterpriseName());
        entity.setEnterpriseType(dto.getEnterpriseType());
        entity.setApplicantId(applicant);

        for (MultipartFile file = dto.getImage0(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage0(name);
        }
        for (MultipartFile file = dto.getImage1(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage1(name);
        }
        for (MultipartFile file = dto.getImage2(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage2(name);
        }
        for (MultipartFile file = dto.getImage3(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage3(name);
        }
        for (MultipartFile file = dto.getImage4(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage4(name);
        }
        for (MultipartFile file = dto.getImage5(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage5(name);
        }

        qualificationMapper.insert(entity);

    }

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

    public EnterpriseQualification qualStatus(Integer id) {
        var entities = qualificationMapper.selectByMap(ofEntries(entry("applicant_id", id)));
        if (entities.size() < 1) {
            return null;
        }
        var entity = entities.get(entities.size() - 1);

        var ret = new EnterpriseQualification();
        ret.setEnterpriseName(entity.getEnterpriseName());
        ret.setEnterpriseType(entity.getEnterpriseType());
        ret.setCurrentStatus(entity.getCurrentStatus());
        ret.setCreatedAt(entity.getCreatedAt());

        return ret;
    }
}
