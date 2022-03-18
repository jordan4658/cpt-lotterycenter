package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.LhcXsad;

/**
 *  Class Name: LhcGodRankingRest.java
 *  Description: 
 *  @author Hans  DateTime 2019年4月5日 下午2:30:02 
 *  @company bvit 
 *  @email hu.peng@bvit.com.cn  
 *  @version 1.0
 */
@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsadRest {
	
	@RequestMapping("/lhcGod/xsad.json")
	PageResult<List<LhcXsad>> getXsad(@RequestParam("eventTitle") String eventTitle, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
	
	@RequestMapping("/lhcGod/addXsad.json")
	void addXsad(@RequestParam("eventTitle") String eventTitle, @RequestParam("iosPhotoUrl") String iosPhotoUrl, @RequestParam("androidPhotoUrl") String androidPhotoUrl, @RequestParam("webPhotoUrl") String webPhotoUrl);
	
	@RequestMapping("/lhcGod/updateXsad.json")
	void updateXsad(@RequestParam("id") String id, @RequestParam("eventTitle") String eventTitle, @RequestParam("iosPhotoUrl") String iosPhotoUrl, @RequestParam("androidPhotoUrl") String androidPhotoUrl, @RequestParam("webPhotoUrl") String webPhotoUrl);
	
	@RequestMapping("/lhcGod/deleteXsad.json")
	boolean deleteXsad(@RequestParam("id") String id);
	
	@RequestMapping("/lhcGod/startupOrStopXsad.json")
	boolean startupOrStopXsad(@RequestParam("id") String id, @RequestParam("activityStatus") String activityStatus);
	
	@RequestMapping("/lhcGod/getXsadById.json")
	LhcXsad getXsadById(@RequestParam("id") String id);
	
	@RequestMapping("/lhcGod/getXsadByActivityStatus.json")
	LhcXsad getXsadByActivityStatus(@RequestParam("activityStatus") int activityStatus);

}
