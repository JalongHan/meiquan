package com.haoqu.meiquan.entity;

/**
 * 省份
 * @author apple
 *
 */
public class Province{

	private String shengfen;



	public Province(String shengfen) {
		super();
		this.shengfen = shengfen;
	}



	public Province() {
		super();
	}



	public String getShengfen() {
		return shengfen;
	}



	public void setShengfen(String shengfen) {
		this.shengfen = shengfen;
	}



	@Override
	public String toString() {
		return "Province [shengfen=" + shengfen + "]";
	}


}
