package com.caipiao.core.library.rest.read.test;

import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;
import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_READ)
public interface TestDemoRest extends BaseRest{

    /**
     * 测试
     * @return
     */
    @GetMapping("/test/demo")
    String selectDemo(@RequestParam("id") int id);

    @GetMapping("/test/testVoid")
    void testVoid();
}
