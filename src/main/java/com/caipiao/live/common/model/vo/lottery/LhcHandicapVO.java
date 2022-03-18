package com.caipiao.live.common.model.vo.lottery;

import com.caipiao.live.common.mybatis.entity.LhcHandicap;

/**
 * @author lzy
 * @create 2018-08-20 17:38
 **/
public class LhcHandicapVO {

    private Integer id;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 开奖时间
     */
    private String startlottoTime;

    /**
     * 说明: 自动开盘时间
     */
    private String startTime;

    /**
     * 说明: 自动封盘时间
     */
    private String endTime;

    /**
     * 说明: 允许自动开盘: 0 不允许,1 允许
     */
    private Integer automation;

    /**
     * 说明: 状态
     */
    private String status;

    public static LhcHandicapVO getInstance(LhcHandicap lhcHandicap) {
        if (lhcHandicap == null) {
            return null;
        }
        LhcHandicapVO lhcHandicapVO = new LhcHandicapVO();
        lhcHandicapVO.setId(lhcHandicap.getId());
        lhcHandicapVO.setIssue(lhcHandicap.getIssue());
        lhcHandicapVO.setStartlottoTime(lhcHandicap.getStartlottoTime());
        lhcHandicapVO.setStartTime(lhcHandicap.getStartTime());
        lhcHandicapVO.setEndTime(lhcHandicap.getEndTime());
        lhcHandicapVO.setAutomation(lhcHandicap.getAutomation());
        return lhcHandicapVO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStartlottoTime() {
        return startlottoTime;
    }

    public void setStartlottoTime(String startlottoTime) {
        this.startlottoTime = startlottoTime;
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

    public Integer getAutomation() {
        return automation;
    }

    public void setAutomation(Integer automation) {
        this.automation = automation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
