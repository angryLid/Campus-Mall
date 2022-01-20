package io.github.angrylid.mall.generated.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author angrylid
 * @since 
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String studentId;

    private String name;

    private String belongTo;

    private Integer relatedUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Integer getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(Integer relatedUser) {
        this.relatedUser = relatedUser;
    }

    @Override
    public String toString() {
        return "Student{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", name=" + name +
        ", belongTo=" + belongTo +
        ", relatedUser=" + relatedUser +
        "}";
    }
}
