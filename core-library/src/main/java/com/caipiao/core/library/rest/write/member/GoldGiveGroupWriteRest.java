package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface GoldGiveGroupWriteRest {

    @PostMapping("/member/addOrUpdateGoldGiveGroup.json")
    void addOrUpdateGoldGiveGroup(@RequestParam("id") Integer id, @RequestParam("name") String name);

    @PostMapping("/member/deleteGoldGiveGroupById.json")
    void deleteGoldGiveGroupById(@RequestParam("id") Integer id);
}
