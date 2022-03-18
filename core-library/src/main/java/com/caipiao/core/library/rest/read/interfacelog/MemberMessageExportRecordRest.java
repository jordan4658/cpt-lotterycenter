package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.MemberMessageExportRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface MemberMessageExportRecordRest extends BaseRest{

    /**
     * 用户信息导出记录列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/listMemberMessageExportRecord.json")
    PageResult<List<MemberMessageExportRecord>> listMemberMessageExportRecord(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
