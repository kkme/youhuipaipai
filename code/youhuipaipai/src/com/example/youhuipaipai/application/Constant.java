package com.example.youhuipaipai.application;

import android.os.Environment;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:Contant.java
 * 
 * 
 * @类描述:常量类
 * 
 * @date:2014-4-8
 * 
 * @Version:1.0
 */
public class Constant {

    /**
     * 服务器地址
     */
    public static final String HOST_URL = "http://115.28.246.230:8088/DataRequestHandler.ashx";

    public static final String IMAGE_URL = "http://115.28.246.230:8088/EditUserImg.aspx";

    public static final String COMMENTS_URL = "http://115.28.246.230:8088/AddComments.aspx";

    /**
     * 是否登录
     */
    public static final String ISLOGIN = "islogin";

    /**
     * 用户id
     */
    public static final String USERID = "userid";
    /**
     * 用户mima
     */
    public static final String USERPASS = "userpass";

    /**
     * 图片缓存地址
     */
    public static final String IMAGE_CAHE = "";

    /**
     * 纬度
     */
    public static final String LAT = "lat";

    /**
     * 经度
     */
    public static final String LON = "lon";

    /**
     * 是否推送
     */
    public static final String IS_PUSH = "ispush";

    /**
     * 第三方登录token
     */
    public static final String TOKEN = "token";

    /**
     * 第三方登录id
     */
    public static final String SHARE_ID = "id";

    /**
     * 我的消息id
     */
    public static final String MESSAGEID = "messageid";

    public static final long TEST = 5000;

    /**
     * 图片地址
     */
    public static final String IMGCACHE = Environment
            .getExternalStorageDirectory() + "/youhuipaipai/imgCache";
    public static final String PICPATH = Environment
    		.getExternalStorageDirectory() + "/youhuipaipai/pic";

}
