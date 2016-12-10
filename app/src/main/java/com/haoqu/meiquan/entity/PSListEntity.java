package com.haoqu.meiquan.entity;

/**
 * 显示首页adapter时用到的实体
 * Created by apple on 16/7/15.
 */
public class PSListEntity {
    private String id;
    private String name;
    private String title;
    private String thumb;
    private String marketprice;
    private String costprice;
    private String yuanjia;
    private String description;
    private String sID;
    private String showsales;

    public PSListEntity() {
    }

    public PSListEntity(String costprice, String description, String id, String marketprice, String name, String showsales, String sID, String thumb, String title, String yuanjia) {
        this.costprice = costprice;
        this.description = description;
        this.id = id;
        this.marketprice = marketprice;
        this.name = name;
        this.showsales = showsales;
        this.sID = sID;
        this.thumb = thumb;
        this.title = title;
        this.yuanjia = yuanjia;
    }

    @Override
    public String toString() {
        return "PSListEntity{" +
                "costprice='" + costprice + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", marketprice='" + marketprice + '\'' +
                ", yuanjia='" + yuanjia + '\'' +
                ", description='" + description + '\'' +
                ", sID='" + sID + '\'' +
                ", showsales='" + showsales + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowsales() {
        return showsales;
    }

    public void setShowsales(String showsales) {
        this.showsales = showsales;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
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
}
