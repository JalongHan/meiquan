package com.haoqu.meiquan.entity;
/**
 * 好友详细内容*/
public class FriendsList {
	/*已加密的手机号*/
	private String mobile;
	/*昵称*/
	private String nickname;
	/*业绩*/
	private String yeji;
	private String headurl;

	public FriendsList() {
	}

	public FriendsList(String headurl, String mobile, String nickname, String yeji) {
		this.headurl = headurl;
		this.mobile = mobile;
		this.nickname = nickname;
		this.yeji = yeji;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
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

	public String getYeji() {
		return yeji;
	}

	public void setYeji(String yeji) {
		this.yeji = yeji;
	}


	@Override
	public String toString() {
		return "FriendsList{" +
				"headurl='" + headurl + '\'' +
				", mobile='" + mobile + '\'' +
				", nickname='" + nickname + '\'' +
				", yeji='" + yeji + '\'' +
				'}';
	}
}
