package com.haoqu.meiquan.entity;

import java.util.List;
/**
 * 获取行业类
 * @author apple
 *
 */
public class GetTreads {
	private String error;
	private List<Treads> list;
	public GetTreads() {
		super();
	}
	public GetTreads(String error, List<Treads> list) {
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
	public List<Treads> getList() {
		return list;
	}
	public void setList(List<Treads> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "GetTreads [error=" + error + ", list=" + list + "]";
	}



}
