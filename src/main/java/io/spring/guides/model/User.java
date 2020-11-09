package io.spring.guides.model;


/**
 * springtest数据库中user表的持久类
 */
public class User {
    private Long uid; // 主键
    private String uname;
    private String usex;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public User(Long uid, String uname, String usex) {
        this.uid = uid;
        this.uname = uname;
        this.usex = usex;
    }

    // 此处省略setter和getter方法
    @Override
    public String toString() { // 为了方便查看结果，重写了toString方法
        return "User[uid=" + uid + ",uname=" + uname + ",usex=" + usex + "]";
    }
}