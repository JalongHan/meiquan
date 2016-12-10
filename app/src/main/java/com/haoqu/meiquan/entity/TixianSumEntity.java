package com.haoqu.meiquan.entity;
/**
 * 获取我的余额和已成功提现的金额
 * @author apple
 *
 */
public class TixianSumEntity {
	private String error;
	private String mobile;
	private String nickname;
	private String avatar;
	private String money;
	private String oktake;
	public TixianSumEntity() {
		super();
	}


	public TixianSumEntity(String avatar, String error, String mobile, String money, String nickname, String oktake) {
		this.avatar = avatar;
		this.error = error;
		this.mobile = mobile;
		this.money = money;
		this.nickname = nickname;
		this.oktake = oktake;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOktake() {
		return oktake;
	}

	public void setOktake(String oktake) {
		this.oktake = oktake;
	}

	@Override
	public String toString() {
		return "TixianSumEntity{" +
				"avatar='" + avatar + '\'' +
				", error='" + error + '\'' +
				", mobile='" + mobile + '\'' +
				", nickname='" + nickname + '\'' +
				", money='" + money + '\'' +
				", oktake='" + oktake + '\'' +
				'}';
	}
}
