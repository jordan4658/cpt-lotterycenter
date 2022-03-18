package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface ReturnMessageWriteRest {

    @PostMapping("/member/issueReturnMessage.json")
    boolean issueReturnMessage(@RequestParam("message") String message, @RequestParam("accounts") String accounts, @RequestParam("operaterAccount") String operaterAccount);
}
