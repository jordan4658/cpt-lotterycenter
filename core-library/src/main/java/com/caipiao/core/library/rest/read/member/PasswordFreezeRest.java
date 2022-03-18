package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.PasswordFreeze;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface PasswordFreezeRest {

    @GetMapping("/member/pagePasswordFreeze.json")
    PageResult<List<PasswordFreeze>> pagePasswordFreeze(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("memberId") String memberId, @RequestParam("account") String account);
}
