package com.caipiao.core.library.rest.read.system;

import com.caipiao.core.library.dto.operater.OperateLogDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.OperateLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface OperateLogRest extends BaseRest {

    /**
     * 获取管理员操作日志列表
     * @param operateLogDTO
     * @return
     */
    @PostMapping("/listOperateLog.json")
    PageResult<List<OperateLog>> pageOperateLog(@RequestBody OperateLogDTO operateLogDTO);
}
