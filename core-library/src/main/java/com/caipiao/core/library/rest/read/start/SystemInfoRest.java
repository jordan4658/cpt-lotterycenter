package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.vo.start.SystemInfoSetVO;
import com.mapper.domain.SystemInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface SystemInfoRest {

    /**
     * 获取系统设置的信息
     * @return
     */
    @GetMapping("/start/listSystemInfoSet.json")
    SystemInfoSetVO listSystemInfoSet();

    /**
     * 获取产品配置和活动设置的信息
     * @return
     */
    @GetMapping("/start/listProductSet.json")
    Map<String,Object> listProductSet();

}
