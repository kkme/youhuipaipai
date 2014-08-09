package com.example.youhuipaipai.entity;

import java.util.List;

public class AreaAndBusiness {
	private String name;
	private String id;
	private String count;
	private List<AreaAndBusiness> sublist;//地区或者商圈
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<AreaAndBusiness> getSublist() {
		return sublist;
	}
	public void setSublist(List<AreaAndBusiness> sublist) {
		this.sublist = sublist;
	}
	
}
