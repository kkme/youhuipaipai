package com.example.youhuipaipai.entity;

/**
 * @author lvning
 * @version create time:2014-5-6_下午10:24:21
 * @Description TODO
 */
//1 按距离  2 按人数 3 价格低到高  4 高到底  5 最新发布  6 即将结束
public class OrderType {
	public String orderId, orderText;

	public OrderType(String orderId, String orderText) {
		this.orderId = orderId;
		this.orderText = orderText;
	}
}
