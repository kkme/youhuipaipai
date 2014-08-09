package com.estedge.base.data.test;

import java.util.ArrayList;
import java.util.HashMap;

import com.estedge.base.data.DataMiddleware;
import com.example.youhuipaipai.entity.HotCouponList;
import com.example.youhuipaipai.entity.Merchant;

/**************
 * 
 * @项目名:BaseProject
 * 
 * @类名:TestDataMiddleware.java
 * 
 * @创建人:cuijianzhi
 * 
 * @类描述:测试数据中间层
 * 
 * @date:2014-2-11
 * 
 * @Version:1.0
 * 
 ***************************************** 
 */
public class TestDataMiddleware extends DataMiddleware {
   
    private static ArrayList<HotCouponList> couponlist;
    private static ArrayList<Merchant> intergrallist;
    

   

    @Override
    public void getHotCouponsList(HashMap<String, String> mHashmap) {
        
        // TODO Auto-generated method stub
        
    }
    
    
    public void getHotCouponsList11(HashMap<String, String> mHashmap) {
        
        // TODO Auto-generated method stub
        
    }
    /*
     * 首页-优惠券
     * 我的-优惠券
     */
    public static ArrayList<HotCouponList> getHotCouponList(){
        couponlist=new ArrayList<HotCouponList>();
        HotCouponList hc =new HotCouponList();
        for(int i=0;i<10;i++){
          hc.setStarttime("2014.3.15");
          hc.setEndtime("2014.4.20");
          hc.setMerchantname("闻记羊蝎子火锅");
          hc.setIntegral("免费");
          hc.setMerchanttype("火锅");
          hc.setReceived("40");
          couponlist.add(hc);
        }
        return couponlist;
    }
    //首页-积分排行榜
    public static ArrayList<Merchant> getIntergralList(){
        intergrallist=new ArrayList<Merchant>();
        Merchant merchant=new Merchant();
        for(int i=0;i<10;i++){
            merchant.setMerchantname("皇家水晶料理");
            merchant.setClassname("小吃");
            merchant.setPercapita("50");
            merchant.setIntegral("55");
            merchant.setDistance("0.85km");
            merchant.setStarrating("3");
            merchant.setAddress("北辰财富中心");
            intergrallist.add(merchant);
        }
        return intergrallist;
        
    }
}
