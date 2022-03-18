package com.caipiao.core.library.rest.write.circle;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CircleRulesWriteRest {
    @PostMapping("/circle/isrtRules.json")
    boolean isrtRules(@RequestParam(name = "id", required = false) String id, @RequestParam("content") String content, @RequestParam("operaterAccount") String operaterAccount);
}
