package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface ReturnMessageRest {

    @RequestMapping("/member/pageReturnMessage.json")
    PageResult<List<ReturnMessage>> pageReturnMessage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
