package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.AppNoticeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppNoticeWriteRest {

    /**
     * 添加或修改移动公告
     * @param appNoticeDTO
     */
    @PostMapping("/start/addOrUpdateAppNotice.json")
    void addOrUpdateAppNotice(@RequestBody AppNoticeDTO appNoticeDTO);

    /**
     * 删除移动公告
     * @param appNoticeId
     */
    @PostMapping("/start/deleteAppNoticeById.json")
    void deleteAppNoticeById(@RequestParam("appNoticeId") Integer appNoticeId);
}
