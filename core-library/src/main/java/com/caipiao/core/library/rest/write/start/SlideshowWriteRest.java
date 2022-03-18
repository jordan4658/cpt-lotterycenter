package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.SlideshowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface SlideshowWriteRest {

    @PostMapping("/start/addOrUpdateSlideshow.json")
    boolean addOrUpdateSlideshow(@RequestBody SlideshowDTO slideshowDTO);

    @PostMapping("/start/deleteSlideshowById.json")
    boolean deleteSlideshowById(@RequestParam("activityId") Integer activityId);
}
