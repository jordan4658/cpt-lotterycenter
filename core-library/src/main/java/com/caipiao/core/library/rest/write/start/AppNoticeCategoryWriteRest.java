package com.caipiao.core.library.rest.write.start;

import com.mapper.domain.AppNoticeCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AppNoticeCategoryWriteRest {


    /**
     * 添加或修改移动公告分类
     * @param id
     * @param name
     */
    @PostMapping("/start/addOrUpdateAppNoticeCategory.json")
    void addOrUpdateAppNoticeCategory(@RequestParam("id") Integer id, @RequestParam("name") String name);

    /**
     * 根据id删除公告分类
     * @param id
     */
    @PostMapping("/start/deleteAppNoticeCategoryById.json")
    void deleteAppNoticeCategoryById(@RequestParam("id") Integer id);
}
