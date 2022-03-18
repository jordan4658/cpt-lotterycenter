package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.AppVersionVO;
import com.mapper.domain.AppVersion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppVersionRest {

    /**
     * App版本管理列表
     * @param pageNum
     * @param pageSize
     * @param appId
     * @param versionId
     * @return
     */
    @GetMapping("/start/listAppVersion.json")
    PageResult<List<AppVersionVO>> listAppVersion(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("appId") Integer appId, @RequestParam("versionId") Integer versionId);

    @GetMapping("/start/findAppVersionById.json")
    AppVersion findAppVersionById(@RequestParam("appVersionId") Integer appVersionId);

    /**
     * 根据appId查询APP版本
     * @param appId
     * @return
     */
    @GetMapping("/start/listAppVersionVO.json")
    List<AppVersionVO> listAppVersionVO(@RequestParam("appId") Integer appId);
}
