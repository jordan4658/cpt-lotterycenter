package com.caipiao.core.library.rest.read.startlotto;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.startlotto.OpenRepealOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface OpenRepealOrderRest {

    /**
     * 开放撤单列表
     * @param pageNum
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @return
     */
    @GetMapping("/pageOpenRepealOrder,json")
    PageResult<List<OpenRepealOrderVO>> pageOpenRepealOrder(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue);
}
