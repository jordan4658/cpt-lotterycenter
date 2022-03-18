package com.caipiao.core.library.dto.result;

/**
 * 澳洲pk10两面
 *
 * @author lzy
 * @create 2018-09-04 11:19
 **/
public class FivepksLiangMian {

    private Integer da;

    private Integer xiao;

    private Integer dan;

    private Integer shuang;

    private Integer lon; //龙

    private Integer hu;

    public FivepksLiangMian() {
    }

    public FivepksLiangMian(Integer da, Integer xiao, Integer dan, Integer shuang) {
        this.da = da;
        this.xiao = xiao;
        this.dan = dan;
        this.shuang = shuang;
    }

    public FivepksLiangMian(Integer da, Integer xiao, Integer dan, Integer shuang, Integer lon, Integer hu) {
        this.da = da;
        this.xiao = xiao;
        this.dan = dan;
        this.shuang = shuang;
        this.lon = lon;
        this.hu = hu;
    }

    public Integer getDa() {
        return da;
    }

    public void setDa(Integer da) {
        this.da = da;
    }

    public Integer getXiao() {
        return xiao;
    }

    public void setXiao(Integer xiao) {
        this.xiao = xiao;
    }

    public Integer getDan() {
        return dan;
    }

    public void setDan(Integer dan) {
        this.dan = dan;
    }

    public Integer getShuang() {
        return shuang;
    }

    public void setShuang(Integer shuang) {
        this.shuang = shuang;
    }

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public Integer getHu() {
        return hu;
    }

    public void setHu(Integer hu) {
        this.hu = hu;
    }
}
