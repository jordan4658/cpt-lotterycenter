package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.AppNoticeCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppNoticeCategoryRest {

    /**
     * 移动公告分类列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/start/listAppNoticeCategory.json")
    PageResult<List<AppNoticeCategory>> listAppNoticeCategory(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据id获取移动公告分类
     * @param categoryId
     * @return
     */
    @GetMapping("/start/findAppNoticeCategory.json")
    AppNoticeCategory findAppNoticeCategory(@RequestParam("categoryId") Integer categoryId);

    /**
     * 获取所有的移动公告分类
     * @return
     */
    @GetMapping("/start/findAllAppNoticeCategory.json")
    List<AppNoticeCategory> findAllAppNoticeCategory();

}
