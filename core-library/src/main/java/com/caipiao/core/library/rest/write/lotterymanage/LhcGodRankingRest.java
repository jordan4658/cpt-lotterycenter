package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.LhcGodRanking;

/**
 *  Class Name: LhcGodRankingRest.java
 *  Description: 
 *  @author Hans  DateTime 2019年4月5日 下午2:30:02 
 *  @company bvit 
 *  @email hu.peng@bvit.com.cn  
 *  @version 1.0
 */
@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcGodRankingRest {
	
	@RequestMapping("/lhcGod/getGodRaning.json")
	PageResult<List<LhcGodRanking>> getGodRanking(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("godtype") Integer godtype);
	
	@RequestMapping("/lhcGod/getGodRankingByType.json")
	PageResult<List<LhcGodRanking>> getGodRankingByType(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
	
	@RequestMapping("/lhcGod/updateGodranking.json")
	boolean updateGodranking(@RequestParam("list") String list);
	
	@RequestMapping("/lhcGod/deleteAllGodranking.json")
	boolean deleteAllGodranking(@RequestParam("list") String list);
	
	@RequestMapping("/lottery/getWebGodRankingByType.json")
	public PageResult<List<LhcGodRanking>> getWebGodRankingByType(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
