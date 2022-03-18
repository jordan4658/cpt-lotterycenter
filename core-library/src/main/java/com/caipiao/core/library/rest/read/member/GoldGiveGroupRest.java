package com.caipiao.core.library.rest.read.member;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.GoldGiveGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface GoldGiveGroupRest {

    @GetMapping("/member/pageGoldGiveGroup.json")
    PageResult<List<GoldGiveGroup>> pageGoldGiveGroup(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/member/findGoldGiveGroup.json")
    GoldGiveGroup findGoldGiveGroup(@RequestParam("groupId") Integer groupId);

}
