package com.haoqu.meiquan.entity;
/**
 * 解绑银行卡返回
 * @author apple
 *
 */
public class ErrorCodeEntity {
	private String error;

	public ErrorCodeEntity(String error) {
		super();
		this.error = error;
	}

	public ErrorCodeEntity() {
		super();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


}
