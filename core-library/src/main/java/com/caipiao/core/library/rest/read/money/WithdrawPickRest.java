package com.caipiao.core.library.rest.read.money;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.HashMap;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.WithdrawPick;

@FeignClient(name = BUSINESS_READ)
public interface WithdrawPickRest {

    @GetMapping("/money/pageWithdrawPick.json")
    PageResult<List<WithdrawPick>> pageWithdrawPick(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam("account") String account, @RequestParam("vip") String vip, @RequestParam("orderCode") String orderCode, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
                                                    @RequestParam("status") String status, @RequestParam("opid") String opid);
    
    @GetMapping("/money/pagefullWithdrawPick.json")
    PageResult<List<HashMap<String, Object>>> pagefullWithdrawPick(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                                                   @RequestParam("account") String account, @RequestParam("vip") String vip, @RequestParam("orderCode") String orderCode, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
                                                                   @RequestParam("status") String status, @RequestParam("opid") String opid);
    
    @GetMapping("/money/getsumfullWithdrawPick.json")
    HashMap<String, Object> getsumfullWithdrawPick(
            @RequestParam("account") String account, @RequestParam("vip") String vip, @RequestParam("orderCode") String orderCode, @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
            @RequestParam("status") String status, @RequestParam("opid") String opid);

    @GetMapping("/money/findWithdrawPickById.json")
    WithdrawPick findWithdrawPickById(@RequestParam("id") Integer id);

   
}
