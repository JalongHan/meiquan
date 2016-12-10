package com.haoqu.meiquan.entity;
/**
 * 提现记录列表
 * @author apple
 *
 */
public class TixianList {
	private String tid;
	private String money;
	private String createdate;
	private String status;


	public TixianList() {
		super();
	}
	public TixianList(String tid, String money, String createdate, String status) {
		super();
		this.tid = tid;
		this.money = money;
		this.createdate = createdate;
		this.status = status;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TixianList [tid=" + tid + ", money=" + money + ", createdate="
				+ createdate + ", status=" + status + "]";
	}


}
