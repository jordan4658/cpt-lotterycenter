package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapper.domain.SupportBank;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface BankCardWriteRest {

    @PostMapping("/member/deleteCard.json")
    boolean deleteCard(@RequestParam("id") Integer id);
    
    @PostMapping("/member/addBankCard.json")
    String addBankCard(@RequestParam("memberId") String memberId, @RequestParam("bankid") String bankid, @RequestParam("cardNumber") String cardNumber);
    
    
    
    @PostMapping("/member/getsupportbankbycard.json")
     SupportBank getsupportbankbycard(@RequestParam("cardNumber") String cardNumber) ;
}
