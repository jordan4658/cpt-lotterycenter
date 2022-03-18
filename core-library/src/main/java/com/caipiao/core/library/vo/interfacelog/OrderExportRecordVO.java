package com.caipiao.core.library.vo.interfacelog;

/**
 * @author lzy
 * @create 2018-06-19 17:52
 **/
public class OrderExportRecordVO {

    private Integer id;

    /**
     * 说明: 彩种id
     */
    private Integer lotteryId;

    /**
     * 说明: 彩种名称
     */
    private String lotteryName;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 文件名称
     */
    private String name;

    /**
     * 说明: 文件下载路径
     */
    private String url;

    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
