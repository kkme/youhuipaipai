package com.estedge.base.data;

import java.util.HashMap;

/****************
 * 
 * @项目名:BaseProject
 * 
 * @类名:DataMiddleware.java
 * 
 * @创建人:cuijianzhi
 * 
 * @类描述:数据中间层
 * 
 * @date:2014-2-11
 * 
 * @Version:1.0
 * 
 ***************************************** 
 */
public abstract class DataMiddleware {

	/**
	 * 得到题目信息
	 * 
	 * @param questionId
	 * @return
	 */
	
	/****
	 * 最热优惠券列表
	 * @param id
	 */
	public abstract void getHotCouponsList(HashMap<String, String> mHashmap);

}
