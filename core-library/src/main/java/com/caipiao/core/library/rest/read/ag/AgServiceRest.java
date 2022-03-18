package com.caipiao.core.library.rest.read.ag;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.AppMember;
import com.mapper.domain.OperateSensitiveLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * AG跳转
 */
@FeignClient(name = BUSINESS_READ)
public interface AgServiceRest extends BaseRest {
	

    @PostMapping("ag/queryAg.json")
	void queryAg(OperateSensitiveLog operateSensitiveLog);
    
    @PostMapping("ag/agJump.json")
    AppMember agJump(@RequestParam("acount") String acount);

}
