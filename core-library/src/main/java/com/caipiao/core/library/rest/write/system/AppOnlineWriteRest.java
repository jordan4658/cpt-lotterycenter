package com.caipiao.core.library.rest.write.system;

import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppOnlineWriteRest extends BaseRest{


    /**
     * 刷新app在线用户数量
     */
    @PostMapping("appOnline/refurbishAppOnlineNum.json")
    void refurbishAppOnlineNum();
}
