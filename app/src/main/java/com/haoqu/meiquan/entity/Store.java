package com.haoqu.meiquan.entity;
/**
 * 商铺类
 * @author apple
 *
 */
public class Store {
	private String mallname;
	private String id;

	public Store() {
		super();
	}
	public Store(String mallname, String id) {
		super();
		this.mallname = mallname;
		this.id = id;
	}
	public String getMallname() {
		return mallname;
	}
	public void setMallname(String mallname) {
		this.mallname = mallname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Store [mallname=" + mallname + ", id=" + id + "]";
	}


}	
