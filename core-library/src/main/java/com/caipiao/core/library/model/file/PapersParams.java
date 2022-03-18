package com.caipiao.core.library.model.file;

/**
 * 上传文件信息
 */
public class PapersParams {

    /**
     * 上传编码
     */
    private String uploadCode;

    /**
     * 文件分类
     */
    private String papersType;

    /**
     * 图片还是文件
     * 0:图片，1：视频，2：其它
     */
    private int imgOrFile;

    /**
     * 图片所属功能
     */
    private String funcName;

    /**
     * 是否需要水印 TODO 待开发
     */
    private boolean needWatermark;

    /**
     * 是否需要保存源图/文件
     */
    private boolean saveSource;

    /**
     * 是否需要其他size图片
     */
    private boolean needOtherSize;

    /**
     * 默认size（宽,高）
     */
    private String defaultSize;

    /**
     * 中图size（宽,高）
     */
    private String middleSize;

    /**
     * 小图size（宽,高）
     */
    private String smallSize;

    /**
     * 文件URI
     */
    private String uri;

    /**
     * 是否需要置换长宽（非设计比例，需要置换再生成缩略图）
     */
    private boolean needExchangeSize;

    /**
     * 保持图片不变形，多余部分裁剪掉（非默认图片）
     */
    private boolean needCut;


    public String getUploadCode() {
        return uploadCode;
    }

    public void setUploadCode(String uploadCode) {
        this.uploadCode = uploadCode;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public boolean isNeedWatermark() {
        return needWatermark;
    }

    public void setNeedWatermark(boolean needWatermark) {
        this.needWatermark = needWatermark;
    }

    public String getSmallSize() {
        return smallSize;
    }

    public void setSmallSize(String smallSize) {
        this.smallSize = smallSize;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSaveSource() {
        return saveSource;
    }

    public void setSaveSource(boolean saveSource) {
        this.saveSource = saveSource;
    }

    public boolean isNeedOtherSize() {
        return needOtherSize;
    }

    public void setNeedOtherSize(boolean needOtherSize) {
        this.needOtherSize = needOtherSize;
    }

    public String getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(String defaultSize) {
        this.defaultSize = defaultSize;
    }

    public String getMiddleSize() {
        return middleSize;
    }

    public void setMiddleSize(String middleSize) {
        this.middleSize = middleSize;
    }

    public boolean isNeedCut() {
        return needCut;
    }

    public void setNeedCut(boolean needCut) {
        this.needCut = needCut;
    }

    public boolean isNeedExchangeSize() {
        return needExchangeSize;
    }

    public void setNeedExchangeSize(boolean needExchangeSize) {
        this.needExchangeSize = needExchangeSize;
    }

    public String getPapersType() {
        return papersType;
    }

    public void setPapersType(String papersType) {
        this.papersType = papersType;
    }

    public int getImgOrFile() {
        return imgOrFile;
    }

    public void setImgOrFile(int imgOrFile) {
        this.imgOrFile = imgOrFile;
    }

    @Override
    public String toString() {
        return "PapersParams{" +
                "uploadCode='" + uploadCode + '\'' +
                ", papersType='" + papersType + '\'' +
                ", imgOrFile=" + imgOrFile +
                ", funcName='" + funcName + '\'' +
                ", needWatermark=" + needWatermark +
                ", saveSource=" + saveSource +
                ", needOtherSize=" + needOtherSize +
                ", defaultSize='" + defaultSize + '\'' +
                ", middleSize='" + middleSize + '\'' +
                ", smallSize='" + smallSize + '\'' +
                ", uri='" + uri + '\'' +
                ", needExchangeSize=" + needExchangeSize +
                ", needCut=" + needCut +
                '}';
    }
}
