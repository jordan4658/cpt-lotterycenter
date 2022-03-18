package com.caipiao.core.library.rest.read.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_READ;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;

@FeignClient(name = XSRDCOMMEND_READ)
public interface LhcXsRecommendAdmireRest {

	/**
	 * 查询单个
	 * @param recommendId 推荐Id
	 * @param memberId 用户Id
	 * @return
	 */
    @GetMapping("/lhcXspl/queryByRecommend.json")
    ResultInfo<Map<String,Object>> queryByRecommend(
            @RequestParam("recommendId") Integer recommendId, @RequestParam("memberId") Integer memberId);
}
