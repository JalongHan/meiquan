package com.haoqu.meiquan.entity;

import java.util.List;

/**
 * APP商品销量排行
 * Created by apple on 16/7/14.
 */
public class GetHotGoodsList {
    private String error;
    private List<HotGoodsList> list;

    public GetHotGoodsList() {
    }

    public GetHotGoodsList(String error, List<HotGoodsList> list) {
        this.error = error;
        this.list = list;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setList(List<HotGoodsList> list) {
        this.list = list;
    }

    public List<HotGoodsList> getList() {
        return list;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "GetHotGoodsList{" +
                "error='" + error + '\'' +
                ", list=" + list +
                '}';
    }
}
