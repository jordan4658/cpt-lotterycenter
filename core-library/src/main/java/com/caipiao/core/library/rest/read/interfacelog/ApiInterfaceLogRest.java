package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.dto.interfacelog.ApiInterfaceLogDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.ApiInterfaceLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface ApiInterfaceLogRest extends BaseRest {

    /**
     * API接口日志列表
     * @param apiInterfaceLogDTO
     * @return
     */
    @PostMapping("/listApiInterfaceLog.json")
    PageResult<List<ApiInterfaceLog>> listApiInterfaceLog(@RequestBody ApiInterfaceLogDTO apiInterfaceLogDTO);
}
