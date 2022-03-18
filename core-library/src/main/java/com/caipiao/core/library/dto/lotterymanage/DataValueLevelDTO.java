package com.caipiao.core.library.dto.lotterymanage;

import org.springframework.beans.BeanUtils;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.DataValueLevel;


/**
 * 数据级别
 **/
public class DataValueLevelDTO {

	  /**
     * 字段: data_value_level.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer id;

    
    /**
     * 字段: data_value_level.max_val<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 最大值
     *
     * @mbggenerated
     */
    private String maxVal;

    /**
     * 字段: data_value_level.min_val<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 最小值
     *
     * @mbggenerated
     */
    private String minVal;
    
    /**
     * 字段: data_value_level.code<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 编码
     *
     * @mbggenerated
     */
    private String code;
    
    /**
     * 字段: data_value_level.name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 字段: data_value_level.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: data_value_level.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;
    
    
    /**
     * web端图片
     */
    private String wphoto;
    
    /**
     * 苹果端图片
     */
    private String iphoto;
    
    /**
     * 安卓端图片
     */
    private String aphoto;
    
    
	public String getWphoto() {
		return wphoto;
	}


	public void setWphoto(String wphoto) {
		this.wphoto = wphoto;
	}


	public String getIphoto() {
		return iphoto;
	}


	public void setIphoto(String iphoto) {
		this.iphoto = iphoto;
	}


	public String getAphoto() {
		return aphoto;
	}


	public void setAphoto(String aphoto) {
		this.aphoto = aphoto;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMaxVal() {
		return maxVal;
	}


	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}


	public String getMinVal() {
		return minVal;
	}


	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public Integer getDeleted() {
		return deleted;
	}


	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public DataValueLevel getDataValueLevel() {
		DataValueLevel dataValueLevel = new DataValueLevel();
        BeanUtils.copyProperties(this, dataValueLevel);
//        if (this.id == null) {
//            lhcXsRecommend.setCreateTime(TimeHelper.date());
//        }
        	 if(null!=this.getCreateTime()){
        		 dataValueLevel.setCreateTime(this.getCreateTime());
             }else{
            	 dataValueLevel.setCreateTime(TimeHelper.date());
             }
       
        return dataValueLevel;
    }
    
}
