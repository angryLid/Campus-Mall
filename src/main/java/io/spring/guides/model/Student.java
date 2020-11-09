package io.spring.guides.model;

import java.io.Serializable;

public class Student implements Serializable {

    private String sno;
    private String name;
    private String sex;

    public Student(String sno, String name, String sex) {
        this.sno = sno;
        this.name = name;
        this.sex = sex;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
