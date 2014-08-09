package frame.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.platform.comapi.map.r;
import com.example.youhuipaipai.entity.HotCouponDetail;
import com.example.youhuipaipai.entity.HotCouponList;
import com.example.youhuipaipai.entity.IntegralExchangBean;
import com.example.youhuipaipai.entity.IntegralRecord;
import com.example.youhuipaipai.entity.MemberCard;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.entity.MyComment;
import com.example.youhuipaipai.entity.Mymessage;
import com.example.youhuipaipai.entity.Prize;
import com.example.youhuipaipai.entity.User;

/**
 * @项目名:PrivilegeProgect
 * 
 * @类名:JsonUtil.java
 * 
 * 
 * @类描述:JSON解析工具类
 * 
 * @date:2014-4-8
 * 
 * @Version:1.0
 */
public class JsonUtil {

    /**
     * 获取用户信息
     * 
     * @param json
     * @return
     * @throws JSONException
     *             {"data":{"createdate":"2014-04-10 16:32:00","headimg":"",
     *             "integranum"
     *             :"1011","nickname":"鍙惰惤","sex":"1","thecity":"鍖椾含"
     *             ,"userid":"14"},"info":"OK","result":1}
     */
    public static User jsonToUser(String json) throws JSONException {
        User user = new User();
        JSONObject object = new JSONObject(json).optJSONObject("data");
        user.setHeadimg(object.optString("headimg"));
        user.setIntegranum(object.optString("integranum"));
        user.setMail(object.optString(""));
        user.setNickname(object.optString("nickname"));
        user.setSex(object.optString("sex"));
        user.setThecity(object.optString("thecity"));
        user.setUserid(object.optString("userid"));
        user.setIsFirst(object.optString("isfirst"));
        return user;
    }

    /**
     * 获取错误信息
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static String jsonToErrorMessage(String json) throws JSONException {
        return new JSONObject(json).optString("message");
    }

    /**
     * 获取关注信息
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static ArrayList<Merchant> jsonToMerchants(String json)
            throws JSONException {
        ArrayList<Merchant> merchants = new ArrayList<Merchant>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                Merchant merchant = new Merchant();
                merchant.setAddress(m.optString("address"));
                merchant.setClassname(m.optString("classname"));
                merchant.setDistance(m.optString("distance"));
                merchant.setIcon(m.optString("icon"));
                merchant.setIntegral(m.optString("integral"));
                merchant.setIsdiscount(m.optString("isdiscount"));
                merchant.setIsmember(m.optString("ismember"));
                merchant.setIssign(m.optString("issign"));
                merchant.setLatitude(m.optString("latitude"));
                merchant.setLongitude(m.optString("longitude"));
                merchant.setMerchantid(m.optString("merchantid"));
                merchant.setMerchantname(m.optString("merchantname"));
                merchant.setPercapita(m.optString("percapita"));
                merchant.setStarrating(m.optString("starrating"));
                merchants.add(merchant);
            }
        }
        return merchants;
    }

    /**
     * 获取评论列表
     * 
     * @param json
     * @return
     * @throws JSONException
     *             commentsid评论编号 merchantid商家编号 merchantname商家名字 userid用户Id
     *             starrating星星 hjsatisfaction环境满意度返回80%
     *             fwsatisfaction品质满意度返回80% content内容 imgs评论图片多张图片返回时用逗号分开
     *             datetime评论时间
     */
    public static ArrayList<MyComment> jsonToMyComment(String json)
            throws JSONException {
        ArrayList<MyComment> merchants = new ArrayList<MyComment>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                MyComment merchant = new MyComment();
                merchant.setMerchantid(m.optString("merchantid"));
                merchant.setMerchantname(m.optString("merchantname"));
                merchant.setStarrating(m.optString("starrating"));
                merchant.setCommentsid(m.optString("commentsid"));
                merchant.setContent(m.optString("content"));
                merchant.setDatetime(m.optString("datetime"));
                merchant.setFwsatisfaction(m.optString("fwsatisfaction"));
                merchant.setHjsatisfaction(m.optString("hjsatisfaction"));
                merchant.setPicture(m.optString("imgs"));
                merchant.setUserid(m.optString("userid"));
                merchants.add(merchant);
            }
        }
        return merchants;
    }

    /**
     * 获取我的消息
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static ArrayList<Mymessage> jsonToMymessages(String json)
            throws JSONException {
        ArrayList<Mymessage> mymessages = new ArrayList<Mymessage>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                Mymessage merchant = new Mymessage();
                merchant.setMessageid(m.optString("messageid"));
                merchant.setTitle(m.optString("title"));
                merchant.setContent(m.optString("content"));
                merchant.setDatetime(m.optString("datetime"));
                mymessages.add(merchant);
            }
        }
        return mymessages;
    }

    /**
     * 获取总积分
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static String jsonToScore(String json) throws JSONException {
        return new JSONObject(json).optString("total");
    }

    /**
     * 获取积分记录
     * 
     * @param json
     * @return
     * @throws JSONException
     *             "recordid": "1", "userid": "1", "source": "1", "score":
     *             "100", "datetime": "2014-03-14 15:06:14"
     */
    public static ArrayList<IntegralRecord> jsonToIntegralRecords(String json)
            throws JSONException {
        ArrayList<IntegralRecord> integralRecords = new ArrayList<IntegralRecord>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                IntegralRecord merchant = new IntegralRecord();
                merchant.setRecordid(m.optString("recordid"));
                merchant.setUserid(m.optString("userid"));
                merchant.setScore(m.optString("score"));
                merchant.setSource(m.optString("source"));
                merchant.setDatetime(m.optString("datetime"));
                integralRecords.add(merchant);
            }
        }

        return integralRecords;
    }

    /**
     * 获取优惠券
     * 
     * @param json
     * @return
     * @throws JSONException
     *             couponid优惠券Id merchantid商家Id merchanttype商家类型例：美食、火锅
     *             couponname优惠券名字 merchantname商家名字 integral积分，如果是免费的话，此项为0
     *             starttime优惠开始时间 endtime优惠截止事件 received已领取人数 img图片 state优惠券状态
     *             1 未使用 2 已使用 3 已过期
     */
    public static ArrayList<HotCouponList> jsonToHotCouponLists(String json)
            throws JSONException {
        ArrayList<HotCouponList> integralRecords = new ArrayList<HotCouponList>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                HotCouponList merchant = new HotCouponList();
                merchant.setCouponid(m.optString("couponid"));
                merchant.setCouponname(m.optString("couponname"));
                merchant.setEndtime(m.optString("endtime"));
                merchant.setImg(m.optString("img"));
                merchant.setIntegral(m.optString("integral"));
                merchant.setMerchantid(m.optString("merchantid"));
                merchant.setMerchantname(object.optString("merchantname"));
                merchant.setMerchanttype(m.optString("merchanttype"));
                merchant.setReceived(m.optString("received"));
                merchant.setStarttime(m.optString("starttime"));
                merchant.setState(m.optString("state"));
                integralRecords.add(merchant);
            }
        }

        return integralRecords;
    }

    /**
     * 获取会员卡信息
     * 
     * @param json
     * @return
     * @throws JSONException
     *             name会员卡名字 info简介 content内容 no卡号（已申请成功的才会有此卡号）
     *             starttime有效期开始时间 endtime结束时间 state1 已申请 2 待审核3 已过期
     */
    public static ArrayList<MemberCard> jsonToMemberCards(String json)
            throws JSONException {
        ArrayList<MemberCard> integralRecords = new ArrayList<MemberCard>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                MemberCard merchant = new MemberCard();
                merchant.setName(m.optString("name"));
                merchant.setInfo(m.optString("info"));
                merchant.setContent(m.optString("content"));
                merchant.setNo(m.optString("no"));
                merchant.setId(m.optString("id"));
                merchant.setIntegral(m.optString("integral"));
                merchant.setEndtime(m.optString("endtime"));
                merchant.setStarttime(m.optString("starttime"));
                merchant.setState(m.optString("state"));
                merchant.setReceiver(m.optString("receiver"));
                integralRecords.add(merchant);
            }
        }
        return integralRecords;
    }

    /**
     * 获取兑换信息
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static ArrayList<IntegralExchangBean> jsonToIntegralExchangBeans(
            String json, int currentType) throws JSONException {
        ArrayList<IntegralExchangBean> integralRecords = new ArrayList<IntegralExchangBean>();
        JSONObject object = new JSONObject(json);
        JSONArray array = object.optJSONArray("data");
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject m = array.optJSONObject(i);
                IntegralExchangBean merchant = new IntegralExchangBean();
                merchant.setImg(m.optString("img"));
                merchant.setIsmember(m.optString("ismember"));
                merchant.setTitile(m.optString("title"));
                merchant.setId(m.optString("id"));
                merchant.setContent(m.optString("content"));
                merchant.setCount(m.optString("count"));
                merchant.setIntegral(m.optString("integral"));
                merchant.setEndtime(m.optString("endtime"));
                merchant.setCurrentType(currentType);
                merchant.setStarttime(m.optString("starttime"));
                merchant.setState(m.optString("state"));
                merchant.setType(m.optString("type"));
                integralRecords.add(merchant);
            }
        }
        return integralRecords;
    }

    /**
     * 获取兑换信息
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    public static IntegralExchangBean jsonToIntegralExchangBean(String json)
            throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject m = object.optJSONObject("data");
        IntegralExchangBean merchant = new IntegralExchangBean();
        merchant.setImg(m.optString("img"));
        merchant.setIsmember(m.optString("ismember"));
        merchant.setTitile(m.optString("title"));
        merchant.setId(m.optString("id"));
        merchant.setContent(m.optString("content"));
        merchant.setCount(m.optString("count"));
        merchant.setIntegral(m.optString("integral"));
        merchant.setEndtime(m.optString("endtime"));
        merchant.setStarttime(m.optString("starttime"));
        merchant.setState(m.optString("state"));
        merchant.setType(m.optString("type"));
        return merchant;
    }

    /**
     * 获取会员卡详情
     * 
     * @param json
     * @return
     * @throws JSONException
     *             name会员卡名字 info简介 content内容 no卡号（已申请成功的才会有此卡号）
     *             starttime有效期开始时间 endtime结束时间 state 1 已申请 2 待审核 3 未申请 4 已过期
     *             integral申请会员卡需要的积分 =0时表示该会员卡不需要积分，免费
     */
    public static MemberCard jsonToMemberCard(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject array = object.optJSONObject("data");
        if (null != array) {
            MemberCard merchant = new MemberCard();
            merchant.setName(array.optString("name"));
            merchant.setInfo(array.optString("info"));
            merchant.setContent(array.optString("content"));
            merchant.setNo(array.optString("no"));
            merchant.setIntegral(array.optString("integral"));
            merchant.setEndtime(array.optString("endtime"));
            merchant.setStarttime(array.optString("starttime"));
            merchant.setState(array.optString("state"));
            return merchant;
        }
        return null;
    }

    /**
     * 获取奖品详情
     * 
     * @param json
     * @return
     * @throws JSONException
     *             "id": "1", "img": "1", "count": "1", "integral": "1",
     *             "overtime": "1", "title": "1", "info": "1", "address": "1"
     */
    public static Prize jsonToPrize(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject array = object.optJSONObject("data");
        if (null != array) {
            Prize merchant = new Prize();
            merchant.setAddress(array.optString("address"));
            merchant.setInfo(array.optString("info"));
            merchant.setCount(array.optString("count"));
            merchant.setId(array.optString("id"));
            merchant.setIntegral(array.optString("integral"));
            merchant.setImg(array.optString("img"));
            merchant.setOvertime(array.optString("overtime"));
            merchant.setTitle(array.optString("title"));
            return merchant;
        }
        return null;
    }

    /**
     * 获取优惠券信息
     * 
     * @param json
     * @return
     * @throws JSONException
     *             couponid优惠券Id couponname优惠券名字 couponinfo优惠券简介
     *             couponcontent优惠券优惠内容 starttime开始时间 endtime结束时间 merchantid商家编号
     *             merchantname商家名字 starrating星星 percapita人均 address地址
     *             phonenum电话 longitude经度 latitude纬度 couponstate 优惠券状态 0 未领取1
     *             已领取未使用 2 已使用3 已过期 couponno 如果已经领取了优惠券此项才会有值否则为“”
     */
    public static HotCouponDetail jsonToHotCouponDetail(String json)
            throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject array = object.optJSONObject("data");
        if (null != array) {
            HotCouponDetail merchant = new HotCouponDetail();
            merchant.setAddress(array.optString("address"));
            merchant.setCouponcontent(array.optString("couponcontent"));
            merchant.setCouponid(array.optString("couponid"));
            merchant.setCouponinfo(array.optString("couponinfo"));
            merchant.setCouponname(array.optString("couponname"));
            merchant.setCouponno(array.optString("couponno"));
            merchant.setEndtime(array.optString("endtime"));
            merchant.setStarttime(array.optString("starttime"));
            merchant.setCouponstate(array.optString("couponstate"));
            merchant.setLatitude(array.optString("latitude"));
            merchant.setLongitude(array.optString("longitude"));
            merchant.setMerchantid(array.optString("merchantid"));
            merchant.setMerchantname(array.optString("merchantname"));
            merchant.setPercapita(array.optString("percapita"));
            merchant.setPhonenum(array.optString("phonenum"));
            merchant.setStarrating(array.optString("starrating"));
            merchant.setReceiver(array.optString("receiver"));

            return merchant;
        }
        return null;
    }

}
