package com.caipiao.core.library.rest.read.result;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;

@FeignClient(name = BUSINESS_READ)
public interface JgsscLotterySgRest {

	
    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/jssscSg/getNewestSgInfo.json")
    Map<String, Object> getNewestSgInfo();	
	
	
    /**
	  *   极光时时彩 - 当天数据
	 * @param type
     * @param pageSize 
     * @param pageNum 
	 * @param string
	 * @return
	 */
    @PostMapping("/jssscSg/todayData.json")
	ResultInfo<List<Map<String, Object>>> todayData(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
  	
    /* 获取历史开奖之开奖101
    * @param type
    * @param issue 期数
    * @return
    */
   @PostMapping("/jssscSg/lishiKaiJiang.json")
   ResultInfo<List<Map<String, Object>>> lishiKaiJiang(@RequestParam("type") String type, @RequestParam("issue") Integer issue);		
	
   /**
    * 今日统计
    * @param type
    * @param date
    * @return
    */
   @PostMapping("/jssscSg/todayCount.json")
   ResultInfo<Map<String, Object>> todayCount(@RequestParam("type") String type, @RequestParam("date") String date);		
	
   /**
    * 获取极光时时彩：公式杀号
    * @param date 日期
    * @param num 球号
    * @param limit 显示条数
    * @return
    */
   @PostMapping("/jssscSg/getJssscGssh.json")
   Map<String, Object> getJgsscGssh(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("num") Integer num);	
	
	
	
	
	
	
}
