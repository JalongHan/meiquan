package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * -APP商户总销量排行（总商城全国的优质商家）
 * Created by apple on 16/7/13.
 */
public class ShopListAllAll {
    private String error;
    private String lasttime;
    private List<ShopListAll> list;

    public ShopListAllAll() {
    }

    public ShopListAllAll(String error, String lasttime, List<ShopListAll> list) {
        this.error = error;
        this.lasttime = lasttime;
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public String getLasttime() {
        return lasttime;
    }

    public List<ShopListAll> getList() {
        return list;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public void setList(List<ShopListAll> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ShopListAllAll{" +
                "error='" + error + '\'' +
                ", lasttime='" + lasttime + '\'' +
                ", list=" + list +
                '}';
    }
}
