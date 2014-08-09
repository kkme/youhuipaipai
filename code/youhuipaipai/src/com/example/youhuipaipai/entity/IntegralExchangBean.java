package com.example.youhuipaipai.entity;

import java.io.Serializable;

/**
 * @类名:IntegralExchangBean.java
 * 
 * @类描述:
 * 
 * @date:2014-4-15
 * 
 * @Version:1.0 id编号 title名称 content抽奖详情 integral积分 starttime开始时间 endtime结束时间
 *              state状态 1 未参与 2 已参与 3 已过期 id编号 title标题 type商家类别例：美食 img图片
 *              integral积分 starttime开始时间 endtime结束时间 state状态 1 未参与 2 已参与 3 已过期
 *              ismember 1 会员卡 2 优惠券 id编号 title名称 img图片 integral积分 state状态 1 未参与
 *              2 已参与 3 已过期 count参加人数
 */
public class IntegralExchangBean implements Serializable {

    private String id;
    private String titile;
    private String content;
    private String integral;
    private String starttime;
    private String endtime;
    private String state;
    private String type;
    private String img;
    private String ismember;
    private int currentType;
    private String count;
    
    

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getCurrentType() {
        return currentType;
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsmember() {
        return ismember;
    }

    public void setIsmember(String ismember) {
        this.ismember = ismember;
    }

}
