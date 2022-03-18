package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.AppNoticeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppNoticeRest {
    /**
     * 移动公告列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    @GetMapping("/start/listAppNotice.json")
    PageResult<List<AppNoticeVO>> listAppNotice(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title);

    /**
     * 根据id查询移动公告
     * @param appNoticeId
     * @return
     */
    @GetMapping("/start/findAppNotice.json")
    AppNoticeVO findAppNotice(@RequestParam("appNoticeId") Integer appNoticeId);

    @GetMapping("/start/findAllAppNotice.json")
    List<AppNoticeVO> findAllAppNotice();
}
