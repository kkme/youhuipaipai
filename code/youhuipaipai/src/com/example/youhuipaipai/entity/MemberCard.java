package com.example.youhuipaipai.entity;

public class MemberCard {
    private String id;
    private String name;
    private String info;// 简介
    private String content;// 内容
    private String no;// 卡号
    private String starttime;// 有效期开始时间
    private String endtime;
    private String state;// 1已申请2待审核3未申请
    private String integral;
    private String receiver;
    private boolean flag = false;
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "id": "1",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "name": "金卡",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "info": "8.5",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "content": "<p>凭生活系会员卡可享受独家8.5折优惠</p>",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "no": "PU2014070810425356",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "starttime": "2014-07-01 12:24:00",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "endtime": "2014-07-31 12:24:00",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "state": "1",
//    07-08 11:20:47.098: E/请求获取数据:(8170):       "receiver": "4"

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
    
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}
