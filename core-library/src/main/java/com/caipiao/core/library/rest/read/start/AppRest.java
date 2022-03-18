package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.AppVO;
import com.mapper.domain.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppRest {

    /**
     * app管理列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/start/listApp.json")
    PageResult<List<App>> listApp(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 通过id获取App信息
     * @param appId
     * @return
     */
    @GetMapping("/start/findAppById.json")
    App findAppById(@RequestParam("appId") Integer appId);

    /**
     * 获取所有的APP
     * @return
     */
    @GetMapping("/start/findAllApp.json")
    List<AppVO> findAllApp();

}
