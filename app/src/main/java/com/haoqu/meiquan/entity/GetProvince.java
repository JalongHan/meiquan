package com.haoqu.meiquan.entity;

import java.util.List;
/**
 * 获得省份类
 * @author apple
 *
 */
public class GetProvince{
	private String error;
	private List<Province> list;

	public GetProvince() {
		super();
	}

	public GetProvince(String error, List<Province> list) {
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

	public List<Province> getList() {
		return list;
	}

	public void setList(List<Province> list) {
		this.list = list;
	}



}
