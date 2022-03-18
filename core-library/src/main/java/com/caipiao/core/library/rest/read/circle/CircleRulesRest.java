package com.caipiao.core.library.rest.read.circle;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.CircleRules;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CircleRulesRest {

    @RequestMapping("/circle/pageCircleRules.json")
    PageResult<List<CircleRules>> pageCircleRules(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping("/circle/getLastOne.json")
    CircleRules getLastOne();
}
