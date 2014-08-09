package com.example.youhuipaipai.entity;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:HotCouponDetail.java
 * 
 * @创建人:liulong
 * 
 * @类描述:优惠券详情
 * 
 * @date:2014-3-20
 * 
 * @Version:1.0
 * 
 ******************************************/
public class HotCouponDetail {

    private String couponid;// 优惠券Id
    private String couponname;// 优惠券名字
    private String couponinfo;// 优惠券简介
    private String couponcontent;// 优惠券 优惠内容
    private String starttime;// 开始时间
    private String endtime;// 结束时间
    private String merchantid;// 商家编号
    private String merchantname;// 商家名字
    private String starrating;// 星星
    private String percapita;// 人均
    private String address;// 地址
    private String phonenum;// 电话
    private String longitude;// 经度
    private String latitude;// 纬度
    private String couponstate;// 优惠券状态 0 未领取 1 已领取未使用 2 已使用 3 已过期
    private String couponno;// 如果已经领取了优惠券 此项才会有值 否则为“”
    private String receiver;// 领取人数

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
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

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getCouponname() {
        return couponname;
    }

    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    public String getCouponinfo() {
        return couponinfo;
    }

    public void setCouponinfo(String couponinfo) {
        this.couponinfo = couponinfo;
    }

    public String getCouponcontent() {
        return couponcontent;
    }

    public void setCouponcontent(String couponcontent) {
        this.couponcontent = couponcontent;
    }

    public String getStarrating() {
        return starrating;
    }

    public void setStarrating(String starrating) {
        this.starrating = starrating;
    }

    public String getPercapita() {
        return percapita;
    }

    public void setPercapita(String percapita) {
        this.percapita = percapita;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCouponstate() {
        return couponstate;
    }

    public void setCouponstate(String couponstate) {
        this.couponstate = couponstate;
    }

    public String getCouponno() {
        return couponno;
    }

    public void setCouponno(String couponno) {
        this.couponno = couponno;
    }

}
