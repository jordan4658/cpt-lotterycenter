package com.caipiao.core.library.rest.write.aop;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.OperateSensitiveLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

/**
 * aop服务请求
 */
@FeignClient(name = BUSINESS_SERVER)
public interface AopWriteServiceRest extends BaseRest {

    @PostMapping("aopServer/addOperateLog.json")
    void saveOperateLogModel(@RequestBody OperateSensitiveLog operateLogModel);

    @PostMapping("aopServer/saveMemberOnline.json")
    void saveMemberOnline(@RequestParam("source") String source, @RequestParam("uid") String uid);

    @PostMapping("aopServer/getSensitive.json")
    List<String> getSensitive();

}
