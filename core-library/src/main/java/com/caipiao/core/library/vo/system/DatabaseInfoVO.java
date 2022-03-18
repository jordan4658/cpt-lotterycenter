package com.caipiao.core.library.vo.system;

/**
 * @Author: admin
 * @Description:数据库表字段VO
 * @Version: 1.0.0
 * @Date; 2017-11-13 9:34
 */
public class DatabaseInfoVO {

    /**
     * 字段名
     */
    private String COLUMN_NAME;

    /**
     * 数据类型（包含长度）
     */
    private String COLUMN_TYPE;

    /**
     *默认值
     */
    private String COLUMN_DEFAULT;

    /**
     * 允许非空
     */
    private String IS_NULLABLE;

    /**
     * 自动递增(auto_increment为自动递增)
     */
    private String EXTRA;

    /**
     * 备注
     */
    private String COLUMN_COMMENT;

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public String getCOLUMN_DEFAULT() {
        return COLUMN_DEFAULT;
    }

    public void setCOLUMN_DEFAULT(String COLUMN_DEFAULT) {
        this.COLUMN_DEFAULT = COLUMN_DEFAULT;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getEXTRA() {
        return EXTRA;
    }

    public void setEXTRA(String EXTRA) {
        this.EXTRA = EXTRA;
    }

    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }

    public void setCOLUMN_COMMENT(String COLUMN_COMMENT) {
        this.COLUMN_COMMENT = COLUMN_COMMENT;
    }
}
