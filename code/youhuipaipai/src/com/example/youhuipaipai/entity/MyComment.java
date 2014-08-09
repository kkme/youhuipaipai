package com.example.youhuipaipai.entity;

import java.io.Serializable;

//我的评论
public class MyComment implements Serializable {
    private String commentsid;// 编号
    private String merchantid;// 用户编号
    private String userid;// 积分来源
    private String merchantname;
    private String starrating;// 星级
    private String fwsatisfaction;// 品质满意度
    private String hjsatisfaction;// 环境满意度
    private String content;// 内容
    private String picture;// 图片
    private String datetime;// 时间
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCommentsid() {
        return commentsid;
    }

    public void setCommentsid(String commentsid) {
        this.commentsid = commentsid;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getStarrating() {
        return starrating;
    }

    public void setStarrating(String starrating) {
        this.starrating = starrating;
    }

    public String getFwsatisfaction() {
        return fwsatisfaction;
    }

    public void setFwsatisfaction(String fwsatisfaction) {
        this.fwsatisfaction = fwsatisfaction;
    }

    public String getHjsatisfaction() {
        return hjsatisfaction;
    }

    public void setHjsatisfaction(String hjsatisfaction) {
        this.hjsatisfaction = hjsatisfaction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
