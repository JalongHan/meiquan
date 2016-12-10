package com.haoqu.meiquan.entity;
/**
 * -APP获取我的余额已成功提现的金额
 * @author apple
 *
 */
public class GetMyMoneyEntity {
	private String error;
	private String mobile;
	private String nickname;
	private String money;
	private String oktake;
	private String isbind;
	public GetMyMoneyEntity() {
		super();
	}
	public GetMyMoneyEntity(String error, String mobile, String nickname,
							String money, String oktake, String isbind) {
		super();
		this.error = error;
		this.mobile = mobile;
		this.nickname = nickname;
		this.money = money;
		this.oktake = oktake;
		this.isbind = isbind;
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
	public String getOktake() {
		return oktake;
	}
	public void setOktake(String oktake) {
		this.oktake = oktake;
	}
	public String getIsbind() {
		return isbind;
	}
	public void setIsbind(String isbind) {
		this.isbind = isbind;
	}
	@Override
	public String toString() {
		return "GetMyMoneyEntity [error=" + error + ", mobile=" + mobile
				+ ", nickname=" + nickname + ", money=" + money + ", oktake="
				+ oktake + ", isbind=" + isbind + "]";
	}


}
