package com.haoqu.meiquan.entity;
/**
 * 商品列表
 * @author apple
 *
 */
public class AppointGoosList {
	//商品ID
	private String id;
	//商品名
	private String title;
	//商品图片（绝对地址）
	private String thumb;
	//商品价格
	private String marketprice;
	//2级佣金
	private String costprice;
	//原价
	private String yuanjia;
	//商品描述
	private String description;
	//sales
	private String sales;
	//f_sales
	private String f_sales;

	public AppointGoosList() {
	}

	public AppointGoosList(String yuanjia, String costprice, String description, String f_sales, String id, String marketprice, String sales, String thumb, String title) {
		this.yuanjia = yuanjia;
		this.costprice = costprice;
		this.description = description;
		this.f_sales = f_sales;
		this.id = id;
		this.marketprice = marketprice;
		this.sales = sales;
		this.thumb = thumb;
		this.title = title;
	}

	public String getCostprice() {
		return costprice;
	}

	public void setCostprice(String costprice) {
		this.costprice = costprice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getF_sales() {
		return f_sales;
	}

	public void setF_sales(String f_sales) {
		this.f_sales = f_sales;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYuanjia() {
		return yuanjia;
	}

	public void setYuanjia(String yuanjia) {
		this.yuanjia = yuanjia;
	}

	@Override
	public String toString() {
		return "AppointGoosList{" +
				"costprice='" + costprice + '\'' +
				", id='" + id + '\'' +
				", title='" + title + '\'' +
				", thumb='" + thumb + '\'' +
				", marketprice='" + marketprice + '\'' +
				", yuanjia='" + yuanjia + '\'' +
				", description='" + description + '\'' +
				", sales='" + sales + '\'' +
				", f_sales='" + f_sales + '\'' +
				'}';
	}
}
