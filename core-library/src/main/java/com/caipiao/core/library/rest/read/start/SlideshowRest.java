package com.caipiao.core.library.rest.read.start;


import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.SlideshowVO;
import com.mapper.domain.Slideshow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface SlideshowRest {

    /**
     * 轮播图列表
     * @param pageNum
     * @param pageSize
     * @param appId
     * @param versionId
     * @return
     */
    @GetMapping("/start/pageSlideshow.json")
    PageResult<List<SlideshowVO>> pageSlideshow(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("appId") Integer appId, @RequestParam("versionId") Integer versionId);

    @GetMapping("/start/getSlideshowById.json")
    SlideshowVO getSlideshowById(@RequestParam("activityId") Integer activityId);
}
