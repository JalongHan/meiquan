package com.haoqu.meiquan.entity;
/**
 * 我的业绩，业绩列表
 * @author apple
 *
 */
public class MyResults {
	private String wxnickname;
	private String openid;
	private String title;
	private String level;
	private String price;
	private String fyp;
	private String paydate;
	public MyResults() {
		super();
	}
	public MyResults(String wxnickname, String openid, String title,
					 String level, String price, String fyp, String paydate) {
		super();
		this.wxnickname = wxnickname;
		this.openid = openid;
		this.title = title;
		this.level = level;
		this.price = price;
		this.fyp = fyp;
		this.paydate = paydate;
	}
	public String getWxnickname() {
		return wxnickname;
	}
	public void setWxnickname(String wxnickname) {
		this.wxnickname = wxnickname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFyp() {
		return fyp;
	}
	public void setFyp(String fyp) {
		this.fyp = fyp;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	@Override
	public String toString() {
		return "MyResults [wxnickname=" + wxnickname + ", openid=" + openid
				+ ", title=" + title + ", level=" + level + ", price=" + price
				+ ", fyp=" + fyp + ", paydate=" + paydate + "]";
	}



}
