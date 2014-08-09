package com.example.youhuipaipai.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public  class BaseMerchant  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3177561533403525250L;
	private int result;
	private String info;
	private ArrayList<Merchant> data;
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
	public ArrayList<Merchant> getData() {
		return data;
	}
	public void setData(ArrayList<Merchant> data) {
		this.data = data;
	}
	
}
