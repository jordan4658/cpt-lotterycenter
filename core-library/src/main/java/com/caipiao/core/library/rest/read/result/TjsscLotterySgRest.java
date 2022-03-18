package com.caipiao.core.library.rest.read.result;

import com.caipiao.core.library.dto.result.CqsscMissDTO;
import com.caipiao.core.library.dto.result.CqsscSizeMissDTO;
import com.caipiao.core.library.dto.result.TjsscMissDTO;
import com.caipiao.core.library.dto.result.TjsscSizeMissDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.MapListVO;
import com.mapper.domain.CqsscLotterySg;
import com.mapper.domain.CqsscRecommend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface TjsscLotterySgRest {

  
    /**
     * 获取天津时时彩：免费推荐列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscRecommends.json")
    Map<String, Object> getTjsscRecommends(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);

    
    /**
     * 获取天津时时彩：公式杀号
     * @param date 日期
     * @param num 球号
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscGssh.json")
    Map<String, Object> getTjsscGssh(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("num") Integer num);    

    
    /**
     * 获取天津时时彩：历史开奖 - 直选走势
     * @param number 1:万位  2:千位  3:百位 4:十位 5:个位
     * @param limit 显示条数
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscTrend.json")
    Map<String, Object> getTjsscTrend(@RequestParam("number") Integer number, @RequestParam("limit") Integer limit);    
    
    /**
	 * 天津时时彩 - 当天数据
	 * @param type
     * @param pageSize 
     * @param pageNum 
	 * @param string
	 * @return
	 */
    @PostMapping("/tjsscSg/todayData.json")
	ResultInfo<List<Map<String, Object>>> todayData(@RequestParam("type") String type, @RequestParam("date") String date, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
    
    
    /**
     * 获取历史开奖之开奖101
     * @param type
     * @param issue 期数
     * @return
     */
    @PostMapping("/tjsscSg/lishiKaiJiang.json")
    ResultInfo<List<Map<String, Object>>> lishiKaiJiang(@RequestParam("type") String type, @RequestParam("issue") Integer issue);    
    
    /**
     * 最近一期的开奖结果
     * @return
     */
    @PostMapping("/tjsscSg/getNewestSgInfo.json")
    Map<String, Object> getNewestSgInfo();    
    
    /**
     * 获取天津时时彩：遗漏统计
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscMissCount.json")
    ResultInfo<List<TjsscMissDTO>> getTjsscMissCount(@RequestParam("date") String date);    
 
    /**
     * 获取天津时时彩：遗漏统计-大小遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscSizeMissCount.json")
    Map<String, List<TjsscSizeMissDTO>> getTjsscSizeMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);    
    
    /**
     * 获取天津时时彩：遗漏统计-单双遗漏
     * @param count 近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @PostMapping("/tjsscSg/getTjsscSingleMissCount.json")
    Map<String, List<TjsscSizeMissDTO>> getTjsscSingleMissCount(@RequestParam("count") Integer count, @RequestParam("number") Integer number);   
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 今日统计
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/tjsscSg/todayCount.json")
    ResultInfo<Map<String, Object>> todayCount(@RequestParam("type") String type, @RequestParam("date") String date);    
    
 
}
