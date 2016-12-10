package com.haoqu.meiquan.entity;

/**
 * 商品列表实体类
 * Created by apple on 16/7/14.
 */
public class HotGoodsList {
    private String id;
    private String showsales;
    private String yuanjia;
    private String marketprice;
    private String title;
    private String description;
    private String thumb;
    private String sortnum;


    public HotGoodsList() {
    }

    public HotGoodsList(String description, String id, String marketprice, String showsales, String sortnum, String thumb, String title, String yuanjia) {
        this.description = description;
        this.id = id;
        this.marketprice = marketprice;
        this.showsales = showsales;
        this.sortnum = sortnum;
        this.thumb = thumb;
        this.title = title;
        this.yuanjia = yuanjia;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public void setShowsales(String showsales) {
        this.showsales = showsales;
    }

    public void setSortnum(String sortnum) {
        this.sortnum = sortnum;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYuanjia(String yuanjia) {
        this.yuanjia = yuanjia;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public String getShowsales() {
        return showsales;
    }

    public String getSortnum() {
        return sortnum;
    }

    public String getThumb() {
        return thumb;
    }

    public String getTitle() {
        return title;
    }

    public String getYuanjia() {
        return yuanjia;
    }

    @Override
    public String toString() {
        return "HotGoodsList{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", showsales='" + showsales + '\'' +
                ", yuanjia='" + yuanjia + '\'' +
                ", marketprice='" + marketprice + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", sortnum='" + sortnum + '\'' +
                '}';
    }
}
