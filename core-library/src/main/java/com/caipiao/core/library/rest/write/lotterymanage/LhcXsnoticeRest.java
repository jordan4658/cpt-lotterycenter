package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;
import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.LhcXsnotice;

/**
 *  Class Name: LhcGodRankingRest.java
 *  Description: 
 *  @author Hans  DateTime 2019年4月5日 下午2:30:02 
 *  @company bvit 
 *  @email hu.peng@bvit.com.cn  
 *  @version 1.0
 */
@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsnoticeRest {
	
	@RequestMapping("/lhcGod/getLhcXsnotice.json")
	PageResult<List<LhcXsnotice>> getLhcXsnotice(@RequestParam("content") String content, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
	
	@RequestMapping("/lhcGod/addXsnotice.json")
	void addXsnotice(@RequestParam("createTime") String createTime, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("content") String content);
	
	@RequestMapping("/lhcGod/startupOrStopXsnotice.json")
	boolean startupOrStopXsnotice(@RequestParam("id") String id, @RequestParam("activityStatus") String activityStatus);
	
	@RequestMapping("/lhcGod/updateXsnotice.json")
	void updateXsnotice(@RequestParam("id") String id, @RequestParam("createTime") String createTime, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("content") String content);
	
	@RequestMapping("/lhcGod/deleteXsnotice.json")
	Boolean deleteXsnotice(@RequestParam("id") String id);
	
	@RequestMapping("/lhcGod/getXsnoticeById.json")
	LhcXsnotice getXsnoticeById(@RequestParam("id") String id);
	
	@RequestMapping("/lhcGod/getXsnoticeByActivityStatus.json")
	LhcXsnotice getXsnoticeByActivityStatus(@RequestParam("activityStatus") int activityStatus);

}
