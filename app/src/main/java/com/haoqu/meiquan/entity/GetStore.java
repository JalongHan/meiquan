package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * 获取商铺类
 * @author apple
 *
 */
public class GetStore {
	private String error;
	private List<Store> list;


	public GetStore() {
		super();
	}


	public GetStore(String error, List<Store> list) {
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


	public List<Store> getList() {
		return list;
	}


	public void setList(List<Store> list) {
		this.list = list;
	}


	@Override
	public String toString() {
		return "GetStore [error=" + error + ", list=" + list + "]";
	}


}
