package com.haoqu.meiquan.entity;

import java.util.List;
/**
 * 获取城市列表类
 * @author apple
 *
 */
public class GetCity {
	private String error;
	private List<City> list;
	public GetCity() {
		super();
	}
	public GetCity(String error, List<City> list) {
		super();
		this.error = error;
		this.list = list;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<City> getList() {
		return list;
	}
	public void setList(List<City> list) {
		this.list = list;
	}



}	
