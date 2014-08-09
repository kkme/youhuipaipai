package com.example.youhuipaipai.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userid;
    private String nickname;// 昵称
    private String sex; // 性别 1:男 2:女
    private String thecity; // 所在城市
    private String headimg; // 头像
    private String Integranum;// 积分
    private String createdate;// 注册时间
    private String mail;// 绑定邮箱
    private String isFirst;// 是否是第一次第三方登录

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getThecity() {
        return thecity;
    }

    public void setThecity(String thecity) {
        this.thecity = thecity;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getIntegranum() {
        return Integranum;
    }

    public void setIntegranum(String integranum) {
        Integranum = integranum;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
