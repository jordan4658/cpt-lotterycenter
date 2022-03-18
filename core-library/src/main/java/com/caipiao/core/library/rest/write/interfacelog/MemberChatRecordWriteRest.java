package com.caipiao.core.library.rest.write.interfacelog;

import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface MemberChatRecordWriteRest extends BaseRest{

    @PostMapping("/log/deleteMemberChatRecordById.json")
    boolean deleteMemberChatRecordById(@RequestParam("recordId") Integer recordId);
}
