package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.ActivityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface ActivityWriteRest {

    @PostMapping("/start/addOrUpdateActivity.json")
    boolean addOrUpdateActivity(@RequestBody ActivityDTO activityDTO);

    @PostMapping("/start/deleteActivityById.json")
    boolean deleteActivityById(@RequestParam("activityId") Integer activityId);
}
