package com.haoqu.meiquan.entity;

import java.util.List;
/**
 * 获得商品列表
 * @author apple
 *
 */
public class GetGoodsListEntity {
	//请求码
	private String error;
	//当前页商品数量
	private String total;
	//list：分类列表（多结果|已按后台设定排序）
	private List<AppointGoosList> list;
	public GetGoodsListEntity() {
		super();
	}
	public GetGoodsListEntity(String error, String total,
							  List<AppointGoosList> list) {
		super();
		this.error = error;
		this.total = total;
		this.list = list;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<AppointGoosList> getList() {
		return list;
	}
	public void setList(List<AppointGoosList> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "GetGoodsListEntity [error=" + error + ", total=" + total
				+ ", list=" + list + "]";
	}



}
