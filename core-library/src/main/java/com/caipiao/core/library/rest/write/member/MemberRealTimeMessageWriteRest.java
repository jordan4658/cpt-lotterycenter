package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface MemberRealTimeMessageWriteRest {

    @GetMapping("/member/issueRealTimeMessage.json")
    boolean issueRealTimeMessage(@RequestParam("message") String message, @RequestParam("accounts") String accounts, @RequestParam("youmeng") Integer youmeng, @RequestParam("account") String account);
}
