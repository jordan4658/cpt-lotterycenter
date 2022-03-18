package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.ActivityVO;
import com.mapper.domain.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface ActivityRest {

    /**
     * 精彩活动列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    @GetMapping("/start/pageActivity.json")
    PageResult<List<Activity>> pageActivity(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title);

    /**
     * 根据id查询精彩活动
     * @param activityId
     * @return
     */
    @GetMapping("/start/findActivity.json")
    Activity findActivity(@RequestParam("activityId") Integer activityId);

    /**
     * 查询所有有效的精彩活动
     * @return
     */
    @GetMapping("/start/findAllActivity.json")
    List<ActivityVO> findAllActivity();
}
