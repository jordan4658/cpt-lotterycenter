package com.caipiao.core.library.dto.appLogin;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2018/7/2 002 14:36
 */
public class TaoBaoIpInfoData {

    //地区名称（华南、华北...）
    private String area;

    //地区编号
    private String area_id;

    //市名称
    private String city;

    //市编号
    private String city_id;

    //国家
    private String country;

    //国家代码
    private String country_id;

    //县名称
    private String county;

    //县编号
    private String county_id;

    //ip
    private String ip;

    //ISP服务商名称（电信/联通/铁通/移动...）
    private String isp;

    //ISP服务商编号
    private String isp_id;

    //省名称
    private String region;

    //省编号
    private String region_id;

    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    @Override
    public String toString() {
        return "TaoBaoIpInfoData{" +
                "area='" + area + '\'' +
                ", area_id='" + area_id + '\'' +
                ", city='" + city + '\'' +
                ", city_id='" + city_id + '\'' +
                ", country='" + country + '\'' +
                ", country_id='" + country_id + '\'' +
                ", county='" + county + '\'' +
                ", county_id='" + county_id + '\'' +
                ", ip='" + ip + '\'' +
                ", isp='" + isp + '\'' +
                ", isp_id='" + isp_id + '\'' +
                ", region='" + region + '\'' +
                ", region_id='" + region_id + '\'' +
                '}';
    }
}
