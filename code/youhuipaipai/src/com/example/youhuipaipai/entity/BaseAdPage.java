package com.example.youhuipaipai.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public  class BaseAdPage  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3177561533403525250L;
	private int result;
	private String info;
	private List<AdPages> data;
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
	public List<AdPages> getData() {
		return data;
	}
	public void setData(List<AdPages> data) {
		this.data = data;
	}
	
}
