package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * 首页个人店铺分类列表
 * @author apple
 *
 */
public class PersonStoreList {
    private String id;
    private String name;
    private String thumb;
    private String isrecommand;
    private String description;
    private List<Goods> goods;

    public PersonStoreList() {
    }

    public PersonStoreList(String description, List<Goods> goods, String id, String isrecommand, String name, String thumb) {
        this.description = description;
        this.goods = goods;
        this.id = id;
        this.isrecommand = isrecommand;
        this.name = name;
        this.thumb = thumb;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsrecommand(String isrecommand) {
        this.isrecommand = isrecommand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public String getId() {
        return id;
    }

    public String getIsrecommand() {
        return isrecommand;
    }

    public String getName() {
        return name;
    }

    public String getThumb() {
        return thumb;
    }

    @Override
    public String toString() {
        return "PersonStoreList{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", thumb='" + thumb + '\'' +
                ", isrecommand='" + isrecommand + '\'' +
                ", goods=" + goods +
                '}';
    }
}
