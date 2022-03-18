package com.caipiao.core.library.vo.system;

import java.util.List;

/**
 * @Author: admin
 * @Description:数据库表信息VO
 * @Version: 1.0.0
 * @Date; 2017-11-13 10:04
 */
public class DatabaseTableVO {

    /**
     * 表名
     */
    private String TABLE_NAME;

    /**
     * 表备注
     */
    private String TABLE_COMMENT;

    /**
     * 表字段相关内容
     */
    private List<DatabaseInfoVO> dataBaseInfo;

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getTABLE_COMMENT() {
        return TABLE_COMMENT;
    }

    public void setTABLE_COMMENT(String TABLE_COMMENT) {
        this.TABLE_COMMENT = TABLE_COMMENT;
    }

    public List<DatabaseInfoVO> getDataBaseInfo() {
        return dataBaseInfo;
    }

    public void setDataBaseInfo(List<DatabaseInfoVO> dataBaseInfo) {
        this.dataBaseInfo = dataBaseInfo;
    }
}
