package com.haoqu.meiquan.entity;

/**
 * 商城首页商品列表list
 * Created by apple on 16/7/15.
 */
public class Goods {
    private String id;
    private String title;
    private String thumb;
    private String marketprice;
    private String costprice;
    private String yuanjia;
    private String description;
    private String showsales;


    public Goods() {
    }

    public Goods(String costprice, String description, String id, String marketprice, String showsales, String thumb, String title, String yuanjia) {
        this.costprice = costprice;
        this.description = description;
        this.id = id;
        this.marketprice = marketprice;
        this.showsales = showsales;
        this.thumb = thumb;
        this.title = title;
        this.yuanjia = yuanjia;
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

    public String getShowsales() {
        return showsales;
    }

    public void setShowsales(String showsales) {
        this.showsales = showsales;
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
        return "Goods{" +
                "costprice='" + costprice + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", marketprice='" + marketprice + '\'' +
                ", yuanjia='" + yuanjia + '\'' +
                ", description='" + description + '\'' +
                ", showsales='" + showsales + '\'' +
                '}';
    }
}
