package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.InstationMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface InstationMessageRest {

    /**
     * 站内信列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/start/listInstationMessage.json")
    PageResult<List<InstationMessage>> listInstationMessage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据id获取站内信
     * @param id
     * @return
     */
    @GetMapping("/start/findInstationMessageById.json")
    InstationMessage findInstationMessageById(@RequestParam("id") Integer id);
}
