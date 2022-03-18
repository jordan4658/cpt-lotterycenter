package com.caipiao.core.library.dto.lotterymanage;

import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcHandicap;

/**
 * @author lzy
 * @create 2018-08-20 16:28
 **/
public class LhcHandicapDTO {

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

    private Integer type;   //1新增2修改

    public LhcHandicap getLhcHandicap() {
        LhcHandicap lhcHandicap = new LhcHandicap();
        lhcHandicap.setId(id);
        if (StringUtils.isNotBlank(issue)) {
            lhcHandicap.setIssue(issue);
        }
        lhcHandicap.setStartlottoTime(startlottoTime);
        if (StringUtils.isNotBlank(startTime)) {
            lhcHandicap.setStartTime(startTime);
        }
        lhcHandicap.setEndTime(endTime);
        if (automation != null) {
            lhcHandicap.setAutomation(automation);
        }
        lhcHandicap.setCreateTime(TimeHelper.date());
        return lhcHandicap;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
