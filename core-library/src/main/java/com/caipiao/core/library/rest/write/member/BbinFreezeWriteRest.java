package com.caipiao.core.library.rest.write.member;

import org.springframework.cloud.openfeign.FeignClient;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface BbinFreezeWriteRest {
}
