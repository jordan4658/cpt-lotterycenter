package com.caipiao.core.library.rest.read.startlotto;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.startlotto.RepealBetOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface RepealBetOrderRest {

    /**
     * 撤销注单列表
     * @param pageNum
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @param orderCode
     * @return
     */
    @GetMapping("/pageRepealBetOrder,json")
    PageResult<List<RepealBetOrderVO>> pageRepealBetOrder(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue, @RequestParam("orderCode") String orderCode);

}
