package com.caipiao.core.library.rest.read.circle;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.CircleGodApply;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CircleGodApplyRest {

    @RequestMapping("/circle/pageGodApply.json")
    PageResult<List<CircleGodApply>> pageGodApply(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("account") String account, @RequestParam("status") Integer status);

}
