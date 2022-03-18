package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.SendDownWay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface SendDownWayRest {

    @GetMapping("/money/pageSendDownWay.json")
    PageResult<List<SendDownWay>> pageSendDownWay(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name);

    @GetMapping("/money/findSendDownWayById.json")
    SendDownWay findSendDownWayById(@RequestParam("id") Integer id);
}
