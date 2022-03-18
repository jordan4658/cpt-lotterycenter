package com.caipiao.core.library.rest.write.start;

import com.mapper.domain.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppWriteRest {

    /**
     * 新增或修改App
     * @param app
     */
    @PostMapping("/start/addOrUpdateApp.json")
    void addOrUpdateApp(@RequestBody App app);

}
