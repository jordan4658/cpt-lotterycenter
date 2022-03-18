package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.BbinFreeze;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface BbinFreezeRest {

    @GetMapping("/member/pageBbinFreeze.json")
    PageResult<List<BbinFreeze>> pageBbinFreeze(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("memberId") String memberId, @RequestParam("account") String account);
}
