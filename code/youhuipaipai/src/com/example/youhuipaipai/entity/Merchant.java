package com.example.youhuipaipai.entity;

import java.io.Serializable;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:Merchant.java
 * 
 * @创建人:liulong
 * 
 * @类描述:优惠商铺列表 首页积分排行榜 首页小编推荐 首页搜索商家
 * 
 * @date:2014-3-21
 * 
 * @Version:1.0
 * 
 ******************************************/
public class Merchant  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String merchantid;
    private String merchantname; // 商家名字
    private String classname; // 分类名字 例如：美食 小吃
    private String percapita; // 人均
    private String integral; // 积分
    private String distance; // 距离
    private String address; // 地址
    private String icon; // 图片
    private String starrating; // 星星
    private String ismember; // 是否有会员卡
    private String isdiscount; // 是否有优惠
    private String issign; // 是否可以签到
    private String longitude; // 经度
    private String latitude; // 纬度
    private String totalpage;// 总页数
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPercapita() {
        return percapita;
    }

    public void setPercapita(String percapita) {
        this.percapita = percapita;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStarrating() {
        return starrating;
    }

    public void setStarrating(String starrating) {
        this.starrating = starrating;
    }

    public String getIsmember() {
        return ismember;
    }

    public void setIsmember(String ismember) {
        this.ismember = ismember;
    }

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

}
