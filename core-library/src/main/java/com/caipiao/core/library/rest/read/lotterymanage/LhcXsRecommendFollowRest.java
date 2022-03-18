package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.lotterymanage.LhcXsRecommendFollowVO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_READ;

@FeignClient(name = XSRDCOMMEND_READ)
public interface LhcXsRecommendFollowRest {

    /**
     * 根据id获取心水推荐评论详情
     * @param id
     * @return
     */
    @GetMapping("/lhcXsgz/selectById.json")
    LhcXsRecommendFollowVO selectById(@RequestParam("id") Integer id);

    @GetMapping("/lhcXsgz/queryPageByMemberId.json")
    PageResult<List<LhcXsRecommendFollowVO>> queryPageByMemberId(
            @RequestParam("memberId") Integer memberId,
            @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize
    );
    
    /**
     * web获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/lhcXsgz/getMemberInfo.json")
    ResultInfo<Map<String, Object>> getMemberInfo(@RequestParam("memberId") Integer memberId);
    
}
