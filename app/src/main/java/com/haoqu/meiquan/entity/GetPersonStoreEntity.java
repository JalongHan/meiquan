package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * 获得首页的个人店铺分类有几款产品实体类
 * @author apple
 *
 */
public class GetPersonStoreEntity {
	private String error;
	private List<PersonStoreList> list;

	public GetPersonStoreEntity() {
	}

	public GetPersonStoreEntity(String error, List<PersonStoreList> list) {
		this.error = error;
		this.list = list;
	}

	public String getError() {
		return error;
	}

	public List<PersonStoreList> getList() {
		return list;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setList(List<PersonStoreList> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "GetPersonStoreEntity{" +
				"error='" + error + '\'' +
				", list=" + list +
				'}';
	}
}
