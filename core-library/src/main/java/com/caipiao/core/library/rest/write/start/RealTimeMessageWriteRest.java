package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.RealTimeMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface RealTimeMessageWriteRest {

    /**
     * 发布实时通知
     * @param realTimeMessageDTO
     */
    @PostMapping("/start/issueRealTimeMessage.json")
    boolean issueRealTimeMessage(@RequestBody RealTimeMessageDTO realTimeMessageDTO);
}
