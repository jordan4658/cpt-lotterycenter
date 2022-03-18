package com.caipiao.core.library.dto.start;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.AdBasic;
import com.mapper.domain.AdPhoto;
import org.apache.commons.lang3.StringUtils;

/**
 * 后台广告管理DTO
 *
 * @author lzy
 * @create 2018-09-06 15:21
 **/
public class AdDTO {

    private Integer id;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 有效开始时间
     */
    private String startTime;

    /**
     * 说明: 有效结束时间
     */
    private String endTime;

    /**
     * 说明: 自动隐藏：0，否；1，是
     */
    private Integer hide;

    /**
     * 说明: 是否关闭：0，否；1，是
     */
    private Integer close;

    /**
     * 说明: 排序值
     */
    private Integer sort;

    /**
     * 说明: 发布系统
     */
    private String publish;

    /**
     * 说明: 安卓图片类型id
     */
    private Integer aid;

    /**
     * 说明: 安卓广告位置id
     */
    private Integer asiteId;

    /**
     * 说明: 安卓图片路径
     */
    private String aphoto;

    /**
     * 说明: 安卓跳转地址
     */
    private String aurl;

    /**
     * 说明: ios图片类型id
     */
    private Integer iid;

    /**
     * 说明: ios广告位置id
     */
    private Integer isiteId;

    /**
     * 说明: ios图片路径
     */
    private String iphoto;

    /**
     * 说明: ios跳转地址
     */
    private String iurl;

    /**
     * 说明: web图片类型id
     */
    private Integer wid;

    /**
     * 说明: web广告位置id
     */
    private Integer wsiteId;

    /**
     * 说明: web图片路径
     */
    private String wphoto;

    /**
     * 说明: web跳转地址
     */
    private String wurl;

    public AdBasic getAdBasic() {
        AdBasic adBasic = new AdBasic();
        adBasic.setId(this.id);
        adBasic.setTitle(this.title);
        adBasic.setStartTime(this.startTime + " 00:00:00");
        adBasic.setEndTime(this.endTime + " 23:59:59");
        adBasic.setHide(this.hide);
        adBasic.setClose(this.close);
        adBasic.setSort(this.sort);
        if (id == null) {
            adBasic.setCreateTime(TimeHelper.date());
        }
        String publish = "";
        if (StringUtils.isNotBlank(this.aphoto)) {
            publish += "安卓,";
        }
        if (StringUtils.isNotBlank(this.iphoto)) {
            publish += "苹果,";
        }
        if (StringUtils.isNotBlank(this.wphoto)) {
            publish += "网页,";
        }
        adBasic.setPublish(publish.substring(0, publish.length()-1));
        return adBasic;
    }

    public AdPhoto getAndroidAdPhoto() {
        if (StringUtils.isBlank(aphoto)) {
            return null;
        }
        AdPhoto adPhoto = new AdPhoto();
        adPhoto.setId(this.aid);
        adPhoto.setType(1);
        adPhoto.setSiteId(this.asiteId);
        adPhoto.setPhoto(this.aphoto);
        adPhoto.setUrl(this.aurl);
        if (aid == null) {
            adPhoto.setBasicId(this.id);
            adPhoto.setCreateTime(TimeHelper.date());
        }
        return adPhoto;
    }

    public AdPhoto getIosAdPhoto() {
        if (StringUtils.isBlank(iphoto)) {
            return null;
        }
        AdPhoto adPhoto = new AdPhoto();
        adPhoto.setId(this.iid);
        adPhoto.setType(2);
        adPhoto.setSiteId(this.isiteId);
        adPhoto.setPhoto(this.iphoto);
        adPhoto.setUrl(this.iurl);
        if (iid == null) {
            adPhoto.setBasicId(this.id);
            adPhoto.setCreateTime(TimeHelper.date());
        }
        return adPhoto;
    }

    public AdPhoto getWebAdPhoto() {
        if (StringUtils.isBlank(wphoto)) {
            return null;
        }
        AdPhoto adPhoto = new AdPhoto();
        adPhoto.setId(this.wid);
        adPhoto.setType(3);
        adPhoto.setSiteId(this.wsiteId);
        adPhoto.setPhoto(this.wphoto);
        adPhoto.setUrl(this.wurl);
        if (wid == null) {
            adPhoto.setBasicId(this.id);
            adPhoto.setCreateTime(TimeHelper.date());
        }
        return adPhoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public Integer getClose() {
        return close;
    }

    public void setClose(Integer close) {
        this.close = close;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getAsiteId() {
        return asiteId;
    }

    public void setAsiteId(Integer asiteId) {
        this.asiteId = asiteId;
    }

    public String getAphoto() {
        return aphoto;
    }

    public void setAphoto(String aphoto) {
        this.aphoto = aphoto;
    }

    public String getAurl() {
        return aurl;
    }

    public void setAurl(String aurl) {
        this.aurl = aurl;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getIsiteId() {
        return isiteId;
    }

    public void setIsiteId(Integer isiteId) {
        this.isiteId = isiteId;
    }

    public String getIphoto() {
        return iphoto;
    }

    public void setIphoto(String iphoto) {
        this.iphoto = iphoto;
    }

    public String getIurl() {
        return iurl;
    }

    public void setIurl(String iurl) {
        this.iurl = iurl;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getWsiteId() {
        return wsiteId;
    }

    public void setWsiteId(Integer wsiteId) {
        this.wsiteId = wsiteId;
    }

    public String getWphoto() {
        return wphoto;
    }

    public void setWphoto(String wphoto) {
        this.wphoto = wphoto;
    }

    public String getWurl() {
        return wurl;
    }

    public void setWurl(String wurl) {
        this.wurl = wurl;
    }
}
