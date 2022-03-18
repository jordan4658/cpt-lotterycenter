package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.PopupActivityVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface PopupActivityRest {
    /**
     * 最新活动列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param appId
     * @param versionId
     * @return
     */
    @GetMapping("/start/listPopupActivity.json")
    PageResult<List<PopupActivityVO>> pagePopupActivity(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title, @RequestParam("appId") Integer appId, @RequestParam("versionId") Integer versionId);

    @GetMapping("/start/getPopupActivityById.json")
    PopupActivityVO getPopupActivityById(@RequestParam("activityId") Integer activityId);
}
