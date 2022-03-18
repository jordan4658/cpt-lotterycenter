package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.lotterymanage.LhcXsRecommendCommentVO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_READ;

@FeignClient(name = XSRDCOMMEND_READ)
public interface LhcXsRecommendCommentRest {

    /**
     * 根据id获取心水推荐评论详情
     * @param id
     * @return
     */
    @GetMapping("/lhcXspl/selectById.json")
    LhcXsRecommendCommentVO selectById(@RequestParam("id") Integer id);

    @GetMapping("/lhcXspl/pagePage.json")
    PageResult<List<LhcXsRecommendCommentVO>> queryPage(
            @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("nickname") String nickname, @RequestParam("comment_nickname") String comment_nickname,
            @RequestParam("referrer") String referrer, @RequestParam("referrerId") Integer referrerId, @RequestParam("createTime_condition_begin") String createTime_condition_begin,
            @RequestParam("createTime_condition_end") String createTime_condition_end, @RequestParam("deleted") Integer deleted
            , @RequestParam("content") String content, @RequestParam("issue") String issue, @RequestParam("recommendId") Integer recommendId
            , @RequestParam("searchWay") Integer searchWay);
    
    
    
    @GetMapping("/lhcXspl/pageRecommendCommendByRecommendId.json")
    PageResult<List<LhcXsRecommendCommentVO>> pageRecommendCommendByRecommendId(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("number") Integer number);
}
