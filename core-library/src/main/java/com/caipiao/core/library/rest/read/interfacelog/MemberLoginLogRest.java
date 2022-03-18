package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.dto.interfacelog.MemberLoginLogDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.MemberLoginLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface MemberLoginLogRest extends BaseRest {
    /**
     * 获取用户登录日志列表
     * @param memberLoginLogDTO
     * @return
     */
    @PostMapping("/pageMemberLoginLog.json")
    PageResult<List<MemberLoginLog>> pageMemberLoginLog(@RequestBody MemberLoginLogDTO memberLoginLogDTO);
}
