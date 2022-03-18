package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.InstationMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface InstationMessageWriteRest {

    /**
     * 添加或修改站内信的信息
     * @param instationMessageDTO
     * @return
     */
    @PostMapping("/start/addOrUpdateInstationMessage.json")
    void addOrUpdateInstationMessage(@RequestBody InstationMessageDTO instationMessageDTO);

    /**
     * 删除站内信的信息
     * @param id
     * @param account
     */
    @PostMapping("/start/deleteInstationMessage.json")
    void deleteInstationMessage(@RequestParam("id") Integer id, @RequestParam("account") String account);
}
