package com.haoqu.meiquan.entity;
/**
 * 获取银行卡绑定信息类
 * @author apple
 *
 */
public class GetBankInfoEntity {
	private String error;
	private String realname;
	private String bankid;
	private String bindtime;
	private String bankname;
	public GetBankInfoEntity() {
		super();
	}
	public GetBankInfoEntity(String error, String realname, String bankid,
							 String bindtime, String bankname) {
		super();
		this.error = error;
		this.realname = realname;
		this.bankid = bankid;
		this.bindtime = bindtime;
		this.bankname = bankname;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBindtime() {
		return bindtime;
	}
	public void setBindtime(String bindtime) {
		this.bindtime = bindtime;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Override
	public String toString() {
		return "GetBankInfoEntity [error=" + error + ", realname=" + realname
				+ ", bankid=" + bankid + ", bindtime=" + bindtime
				+ ", bankname=" + bankname + "]";
	}





}
