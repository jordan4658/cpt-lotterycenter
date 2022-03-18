package com.caipiao.core.library.rest.read.result;

import org.springframework.cloud.openfeign.FeignClient;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;


@FeignClient(name = BUSINESS_READ)
public interface AuspksLotterySgRest {

//    /**
//     * 最近一期的开奖结果
//     * @return
//     */
//    @PostMapping("/auspksSg/getNewestSgInfo.json")
//    ResultInfo<Map<String,Object>> getNewestSgInfo();
//
//    /**
//     * 获取历史开奖
//     * @param type
//     * @param date
//     * @return
//     */
//    @PostMapping("/auspksSg/historySg.json")
//    ResultInfo<List<KjlsVO>> historySg(@RequestParam("type") String type, @RequestParam("date") String date,
//                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
//                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);
////
//
//
//    /**
//     * 获取今日号码资讯
//     * @param type
//     * @return
//     */
//    @PostMapping("/auspksSg/todayNumber.json")
//    ResultInfo<List<MapListVO>> todayNumber(@RequestParam("type") String type);
//
//
//    /**
//     * 获取澳洲PK10 免费推荐列表
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @PostMapping("/auspksSg/recommendList.json")
//    ResultInfo<Map<String, Object>> recommendList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
//
//
//    /**
//     * 获取冷热分析资讯
//     * @param type
//     * @param issue
//     * @return
//     */
//    @PostMapping("/auspksSg/lengRe.json")
//    ResultInfo<List<MapListVO>> lengRe(@RequestParam("type") String type, @RequestParam("issue") Integer issue);
//
//
//    /**
//     * 公式杀号
//     * @param date 日期
//     * @param limit 期数
//     * @param number 第几名
//     * @return
//     */
//    @PostMapping("/auspksSg/killNumber.json")
//    ResultInfo<Map<String,Object>> killNumber(@RequestParam("date") String date, @RequestParam("limit") Integer limit, @RequestParam("number") Integer number);
//
//
//    /**
//     * 获取冠亚和统计
//     * @return
//     */
//    @PostMapping("/auspksSg/guanYaCount.json")
//    ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount();
//
//    /**
//     * 获取两面长龙
//     * @param type
//     * @return
//     */
//    @PostMapping("/auspksSg/liangMianC.json")
//    ResultInfo<List<ThereMemberVO>> liangMianC(@RequestParam("type") String type);
//
//
    
    
    
    
    
	
}
