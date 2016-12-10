package com.haoqu.meiquan.entity;
/**
 * 提现返回的实体类
 * @author apple
 *
 */
public class TiXianEntity {
	private String error;
	private String newmoney;

	public TiXianEntity() {
		super();
	}
	public TiXianEntity(String error, String newmoney) {
		super();
		this.error = error;
		this.newmoney = newmoney;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getNewmoney() {
		return newmoney;
	}
	public void setNewmoney(String newmoney) {
		this.newmoney = newmoney;
	}
	@Override
	public String toString() {
		return "TiXianEntity [error=" + error + ", newmoney=" + newmoney + "]";
	}


}
