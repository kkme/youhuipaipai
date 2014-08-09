package com.example.youhuipaipai.entity;

import java.util.List;


public class MerchantDetail {
	private String merchantid;
	private String merchantname;
	private String classname;
	private String percapita;
	private String starrating;
	private String hjsatisfaction;
	private String fwsatisfaction;
	private String integral;
	private String address;
	private String phonenum;
	private String picture;
	private String ismember;
    private String isdiscount;
    private String issign;
    private String isattention;
    private String longitude;
    private String latitude;
	private List<Coupon> coupons;
	private String imgs;
	private List<Comment>  comments;
	
	private String all;//全部评论个数
    private String good;
    private String bad;
    
    private String attention, sign;
    
	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPercapita() {
		return percapita;
	}

	public void setPercapita(String percapita) {
		this.percapita = percapita;
	}

	public String getStarrating() {
		return starrating;
	}

	public void setStarrating(String starrating) {
		this.starrating = starrating;
	}

	public String getHjsatisfaction() {
		return hjsatisfaction;
	}

	public void setHjsatisfaction(String hjsatisfaction) {
		this.hjsatisfaction = hjsatisfaction;
	}

	public String getFwsatisfaction() {
		return fwsatisfaction;
	}

	public void setFwsatisfaction(String fwsatisfaction) {
		this.fwsatisfaction = fwsatisfaction;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIsmember() {
		return ismember;
	}

	public void setIsmember(String ismember) {
		this.ismember = ismember;
	}

	public String getIsdiscount() {
		return isdiscount;
	}

	public void setIsdiscount(String isdiscount) {
		this.isdiscount = isdiscount;
	}

	public String getIssign() {
		return issign;
	}

	public void setIssign(String issign) {
		this.issign = issign;
	}

	public String getIsattention() {
		return isattention;
	}

	public void setIsattention(String isattention) {
		this.isattention = isattention;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
