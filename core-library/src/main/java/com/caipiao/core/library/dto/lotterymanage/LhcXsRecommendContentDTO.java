package com.caipiao.core.library.dto.lotterymanage;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcXsRecommendContent;
import org.springframework.beans.BeanUtils;

/**
 * @author lzy
 * @create 2018-09-08 15:20
 **/
public class LhcXsRecommendContentDTO {

    private Integer id;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 玩法
     */
    private String play;

    /**
     * 说明: 号码
     */
    private String number;

    /**
     * 说明: 推荐正文id
     */
    private Integer recommendId;

    /**
     * 说明: 开奖结果
     */
    private String sg;

    public LhcXsRecommendContent transformLhcXsRecommendContent() {
        LhcXsRecommendContent lhcXsRecommendContent = new LhcXsRecommendContent();
        BeanUtils.copyProperties(this, lhcXsRecommendContent);
        if (this.id == null) {
            lhcXsRecommendContent.setCreateTime(TimeHelper.date());
        }
        return lhcXsRecommendContent;
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

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }
}
