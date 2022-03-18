package com.caipiao.core.library.rest.read.result;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.KjlsVO;

@FeignClient(name = BUSINESS_READ)
public interface TcplsLotterySgRest {
	
	 /**
     * 获取历史开奖
     * @param type
     * @param date
     * @return
     */
    @PostMapping("/tcplsSg/historySg.json")
    ResultInfo<List<KjlsVO>> historySg(@RequestParam("type") String type, @RequestParam("date") String date,
                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize);
	
    /**
     * 历史开奖
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/tcplsSg/lishiSg.json")
    ResultInfo<Map<String,Object>> lishiSg(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

}
