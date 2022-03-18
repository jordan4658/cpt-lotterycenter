package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.ActivitySetDTO;
import com.caipiao.core.library.dto.start.ProductSetDTO;
import com.caipiao.core.library.dto.start.SystemInfoSetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface SystemInfoWriteRest {

    /**
     * 修改系统设置
     * @param systemInfoSetDTO
     */
    @PostMapping("/start/updateSystemInfoSet.json")
    void updateSystemInfoSet(@RequestBody SystemInfoSetDTO systemInfoSetDTO);

    /**
     * 修改产品设置
     * @param productSetDTO
     */
    @PostMapping("/start/updateProductSet.json")
    void updateProductSet(@RequestBody ProductSetDTO productSetDTO);

    /**
     * 修改活动设置
     * @param activitySetDTO
     */
    @PostMapping("/start/updateActivitySet.json")
    void updateActivitySet(@RequestBody ActivitySetDTO activitySetDTO);
}
