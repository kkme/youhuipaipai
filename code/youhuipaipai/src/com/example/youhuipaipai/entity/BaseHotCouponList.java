package com.example.youhuipaipai.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public  class BaseHotCouponList  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2125502084869885751L;
	/**
	 * 
	 */
	private int result;
	private String info;
	private ArrayList<HotCouponList> data;
	private String totalpage;
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public ArrayList<HotCouponList> getData() {
		return data;
	}
	public void setData(ArrayList<HotCouponList> data) {
		this.data = data;
	}
	
}
