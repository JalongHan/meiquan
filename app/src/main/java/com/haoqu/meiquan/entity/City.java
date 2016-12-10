package com.haoqu.meiquan.entity;
/**
 * 城市类
 * @author apple
 *
 */
public class City {
	private String chengshi;

	public City(String chengshi) {
		super();
		this.chengshi = chengshi;
	}

	public City() {
		super();
	}

	public String getChengshi() {
		return chengshi;
	}

	public void setChengshi(String chengshi) {
		this.chengshi = chengshi;
	}

	@Override
	public String toString() {
		return "City [chengshi=" + chengshi + "]";
	}


}
