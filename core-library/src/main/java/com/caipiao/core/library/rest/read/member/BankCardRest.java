package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.BankCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface BankCardRest {

    @GetMapping("/member/pageBankCard.json")
    PageResult<List<BankCard>> pageBankCard(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("memberId") String memberId,
                                            @RequestParam("account") String account, @RequestParam("number") String number);
    
    @GetMapping("/member/getBankCardbyMid.json")
    List<BankCard> getBankCardbyMid(@RequestParam("memberId") String memberId);
}
