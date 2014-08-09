package com.example.youhuipaipai.entity;

import java.io.Serializable;

public class AdPages  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adpagesid;
	private String adpagesname;
	private String adpagesimg;
	private String adpagesinfo;
	private String datetime;
	private String updatetime;
	public String getAdpagesid() {
		return adpagesid;
	}
	public void setAdpagesid(String adpagesid) {
		this.adpagesid = adpagesid;
	}
	public String getAdpagesname() {
		return adpagesname;
	}
	public void setAdpagesname(String adpagesname) {
		this.adpagesname = adpagesname;
	}
	public String getAdpagesimg() {
		return adpagesimg;
	}
	public void setAdpagesimg(String adpagesimg) {
		this.adpagesimg = adpagesimg;
	}
	public String getAdpagesinfo() {
		return adpagesinfo;
	}
	public void setAdpagesinfo(String adpagesinfo) {
		this.adpagesinfo = adpagesinfo;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
}
