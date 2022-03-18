package com.caipiao.core.library.rest.read.result;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.KjlsVO;
import com.caipiao.core.library.vo.common.MapListVO;

@FeignClient(name = BUSINESS_READ)
public interface FvpksLotterySgRest {

    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/fvpksSg/getNewestSgInfo.json")
    ResultInfo<Map<String,Object>> getNewestSgInfo();	
    
    
    /**
     * 获取历史开奖
     * @param type
     * @param date
     * @return
     */    
    @PostMapping("/fvpksSg/historySg.json")
    ResultInfo<List<KjlsVO>> historySg(@RequestParam("type") String type, @RequestParam("date") String date,
                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);      
    
    
    /**
     * 获取今日号码资讯
     * @param type
     * @return
     */
    @PostMapping("/fvpksSg/todayNumber.json")
    ResultInfo<List<MapListVO>> todayNumber(@RequestParam("type") String type);	    
    
    
    /**
     * 公式杀号
     * @param date 日期
     * @param limit 期数
     * @param number 第几名
     * @return
     */
    @PostMapping("/fvpksSg/killNumber.json")
    ResultInfo<Map<String,Object>> killNumber(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("number") Integer number);     
    
    
    
    
    
    
    
    
    
    
	
}
