package com.haoqu.meiquan.entity;
/**
 * 登陆返回实体类
 * @author apple
 *
 */
public class LoginEntity {
	private String error;
	private String token;
	private String uid;
	private String sid;
	private String level;;
	private String mobile;
	private String nickname;
	private String avatar;

	public LoginEntity() {
		super();
	}


	public LoginEntity(String avatar, String error, String level, String mobile, String nickname, String sid, String token, String uid) {
		this.avatar = avatar;
		this.error = error;
		this.level = level;
		this.mobile = mobile;
		this.nickname = nickname;
		this.sid = sid;
		this.token = token;
		this.uid = uid;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "LoginEntity{" +
				"avatar='" + avatar + '\'' +
				", error='" + error + '\'' +
				", token='" + token + '\'' +
				", uid='" + uid + '\'' +
				", sid='" + sid + '\'' +
				", level='" + level + '\'' +
				", mobile='" + mobile + '\'' +
				", nickname='" + nickname + '\'' +
				'}';
	}
}
