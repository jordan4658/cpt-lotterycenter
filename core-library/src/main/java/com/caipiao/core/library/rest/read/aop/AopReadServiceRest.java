package com.caipiao.core.library.rest.read.aop;

import com.caipiao.core.library.model.aop.AppMemberModel;
import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * aop服务请求
 */
@FeignClient(name = BUSINESS_READ)
public interface AopReadServiceRest extends BaseRest {

    @PostMapping("aopServer/selectAppMember.json")
    AppMemberModel queryAppMemberById(@RequestParam("id") int id);

}
