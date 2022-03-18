package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.FirstRechargeGift;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface FirstRechargeGiftRest {

    @GetMapping("/money/pageFirstRechargeGift.json")
    PageResult<List<FirstRechargeGift>> pageFirstRechargeGift(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("memberId") String memberId, @RequestParam("account") String account, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime);
}
