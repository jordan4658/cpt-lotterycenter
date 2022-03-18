package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.vo.money.CashGiftRecordPageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface GiftMoneyRecordRest {


    @GetMapping("/money/getCashGiftRecordPageVO.json")
    CashGiftRecordPageVO getCashGiftRecordPageVO(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") String type, @RequestParam("startTime") String startTime,
                                                 @RequestParam("endTime") String endTime, @RequestParam("memberId") String memberId, @RequestParam("account") String account, @RequestParam("remark") String remark);
}
