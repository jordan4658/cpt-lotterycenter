package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.RealTimeMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface RealTimeMessageRest {

    /**
     * 实时通知列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/start/listRealTimeMessage.json")
    PageResult<List<RealTimeMessage>> listRealTimeMessage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
