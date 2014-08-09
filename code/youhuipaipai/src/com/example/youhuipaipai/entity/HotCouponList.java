package com.example.youhuipaipai.entity;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:HotCouponList.java
 * 
 * @类描述:首页最热优惠券列表 我的优惠券列表 共用
 * 
 * @date:2014-3-21
 * 
 * @Version:1.0
 * 
 ******************************************/
public class HotCouponList {
    private String couponid;// 优惠券Id
    private String merchantid;// 商家Id
    private String merchanttype; // 商家类型 例：美食、火锅
    private String couponname; // 优惠券名字
    private String merchantname;// 商家名字
    private String integral; // 积分，如果是免费的话，此项为0
    private String starttime; // 优惠开始时间
    private String endtime; // 优惠截止时间
    private String received; // 已领取人数
    private String img; // 图片
    private String state;// 优惠券状态 1 未使用 2 已使用 3 已过期

    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchanttype() {
        return merchanttype;
    }

    public void setMerchanttype(String merchanttype) {
        this.merchanttype = merchanttype;
    }

    public String getCouponname() {
        return couponname;
    }

    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
