package com.haoqu.meiquan.entity;

/**
 * -APP商户总销量排行（总商城全国的优质商家）
 * 其中中的商户列表
 * Created by apple on 16/7/13.
 */
public class ShopListAll {
    private String id;
    private String ygs;
    private String sps;
    private String xl;
    private String mallname;
    private String malldesc;
    private String mallpic;
    private String sortnum;

    public ShopListAll() {
    }

    public ShopListAll(String id, String ygs, String sps, String xl, String mallname, String malldesc, String mallpic, String sortnum) {
        this.id = id;
        this.ygs = ygs;
        this.sps = sps;
        this.xl = xl;
        this.mallname = mallname;
        this.malldesc = malldesc;
        this.mallpic = mallpic;
        this.sortnum = sortnum;
    }

    public String getId() {
        return id;
    }

    public String getMalldesc() {
        return malldesc;
    }

    public String getMallname() {
        return mallname;
    }

    public String getMallpic() {
        return mallpic;
    }

    public String getSortnum() {
        return sortnum;
    }

    public String getSps() {
        return sps;
    }

    public String getXl() {
        return xl;
    }

    public String getYgs() {
        return ygs;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMalldesc(String malldesc) {
        this.malldesc = malldesc;
    }

    public void setMallname(String mallname) {
        this.mallname = mallname;
    }

    public void setMallpic(String mallpic) {
        this.mallpic = mallpic;
    }

    public void setSortnum(String sortnum) {
        this.sortnum = sortnum;
    }

    public void setSps(String sps) {
        this.sps = sps;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public void setYgs(String ygs) {
        this.ygs = ygs;
    }

    @Override
    public String toString() {
        return "ShopListAll{" +
                "id='" + id + '\'' +
                ", ygs='" + ygs + '\'' +
                ", sps='" + sps + '\'' +
                ", xl='" + xl + '\'' +
                ", mallname='" + mallname + '\'' +
                ", malldesc='" + malldesc + '\'' +
                ", mallpic='" + mallpic + '\'' +
                ", sortnum='" + sortnum + '\'' +
                '}';
    }
}
