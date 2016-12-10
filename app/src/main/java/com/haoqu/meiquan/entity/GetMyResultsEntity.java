package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * 获取我的业绩
 */
public class GetMyResultsEntity {
	private String error;
	private String total;
	private String mobile;
	private String nickname;
	private String money;
	private List<MyResults> list;
	public GetMyResultsEntity() {
		super();
	}
	public GetMyResultsEntity(String error, String total, String mobile,
							  String nickname, String money, List<MyResults> list) {
		super();
		this.error = error;
		this.total = total;
		this.mobile = mobile;
		this.nickname = nickname;
		this.money = money;
		this.list = list;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public List<MyResults> getList() {
		return list;
	}
	public void setList(List<MyResults> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "GetMyResultsEntity [error=" + error + ", total=" + total
				+ ", mobile=" + mobile + ", nickname=" + nickname + ", money="
				+ money + ", list=" + list + "]";
	}

}
