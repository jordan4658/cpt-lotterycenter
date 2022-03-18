package com.caipiao.core.library.rest.write.ag;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.OperateSensitiveLog;

/**
 * ag服务请求
 */
@FeignClient(name = BUSINESS_SERVER)
public interface AgWriteServiceRest extends BaseRest {

    @PostMapping("ag/queryAg.json")
    void queryAg(OperateSensitiveLog operateSensitiveLog);

    @PostMapping("ag/agJump.json")
    ResultInfo<String> agJump(@RequestParam("userId") Integer userId,
                              @RequestParam("account") String account,
                              @RequestParam("actype") String actype) throws Exception;

}
