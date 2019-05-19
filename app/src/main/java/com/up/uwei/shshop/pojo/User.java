package com.up.uwei.shshop.pojo;

public class User {
    private int id;
    private String email;
    private String pwd;
    private String qq;
    private String name;
    private String desc;

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
    public User() {}
    public User(int id, String email, String pwd, String qq, String name, String desc) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.qq = qq;
        this.name = name;
        this.desc = desc;
    }

    public User(int id, String email, String pwd, String qq, String name) {
        this(id, email, pwd, qq, name, null);
    }
    public User(int id, String email, String pwd, String qq) {
        this(id, email, pwd, qq, null);
    }
    public User(int id, String email, String pwd) {
        this(id, email, pwd, null);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
