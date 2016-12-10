package com.haoqu.meiquan.entity;
/**
 * 行业类
 * @author apple
 *
 */
public class Treads {
	private String hangye;

	public Treads() {
		super();
	}

	public Treads(String hangye) {
		super();
		this.hangye = hangye;
	}

	public String getHangye() {
		return hangye;
	}

	public void setHangye(String hangye) {
		this.hangye = hangye;
	}

	@Override
	public String toString() {
		return "Treads [hangye=" + hangye + "]";
	}


}
