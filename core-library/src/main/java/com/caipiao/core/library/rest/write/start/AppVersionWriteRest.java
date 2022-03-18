package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.AppVersionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppVersionWriteRest {

    /**
     * 新增或修改APP版本
     * @param appVersionDTO
     */
    @PostMapping("/start/addOrUpateAppVersion.json")
    void addOrUpateAppVersion(@RequestBody AppVersionDTO appVersionDTO);

    /**
     * 根据id删除APP版本
     * @param id
     */
    @PostMapping("/start/deleteAppVersion.json")
    void deleteAppVersion(@RequestParam("id") Integer id);
}
