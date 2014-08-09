package com.example.youhuipaipai.entity;

import java.io.Serializable;


public class AdPagesDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	{"data":{"adpagesid":"10","adpagesimg":"http://115.28.246.230:8088/Img/ContentPrint/20140507015627428.png",
	//"adpagesinfo":"<p>活动时间：2012.12.12</p><p>&nbsp;</p><p>奖品：一等奖 iPad</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二等奖 iPhone</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 三等奖 周杰伦签名一套</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 特等奖 免餐</p><p>&nbsp;</p><p>抽奖方式： 公共平台抽奖，保证公平性。</p><p>&nbsp;</p><p>抽奖地点： 北京市海淀区西小口路东升科技园。</p><p>&nbsp;</p><p>奖品发放： 主办方将在15个工作日内与您联系，保证您尽快收到奖品。</p><p></p>",
	//"adpagesname":"cs","datetime":"2014-03-20 10:13:00","updatetime":"2014-03-20 10:13:48"},"info":"OK","result":1}
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
	public void setAdpagesinfo(String adapagesinfo) {
		this.adpagesinfo = adapagesinfo;
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
