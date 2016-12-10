package com.haoqu.meiquan.entity;

import java.util.List;
/**
 * 好友列表
 * @author apple
 *
 */
public class FriendsListEntity {
	private String error;
	private String fnum;
	private String fsum;
	private String mobile;
	private String nickname;
	private String avatar;
	private List<FriendsList> list;


	public FriendsListEntity() {
		super();
	}

	public FriendsListEntity(String avatar, String error, String fnum, String fsum, List<FriendsList> list, String mobile, String nickname) {
		this.avatar = avatar;
		this.error = error;
		this.fnum = fnum;
		this.fsum = fsum;
		this.list = list;
		this.mobile = mobile;
		this.nickname = nickname;
	}


	@Override
	public String toString() {
		return "FriendsListEntity{" +
				"avatar='" + avatar + '\'' +
				", error='" + error + '\'' +
				", fnum='" + fnum + '\'' +
				", fsum='" + fsum + '\'' +
				", mobile='" + mobile + '\'' +
				", nickname='" + nickname + '\'' +
				", list=" + list +
				'}';
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getFnum() {
		return fnum;
	}

	public void setFnum(String fnum) {
		this.fnum = fnum;
	}

	public String getFsum() {
		return fsum;
	}

	public void setFsum(String fsum) {
		this.fsum = fsum;
	}

	public List<FriendsList> getList() {
		return list;
	}

	public void setList(List<FriendsList> list) {
		this.list = list;
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
}
