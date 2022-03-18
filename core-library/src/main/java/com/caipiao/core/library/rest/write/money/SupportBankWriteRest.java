package com.caipiao.core.library.rest.write.money;

import com.caipiao.core.library.dto.money.SupportBankDTO;
import com.mapper.domain.SupportBank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface SupportBankWriteRest {

    @PostMapping("/money/addOrUpdateSupportBank.json")
    boolean addOrUpdateSupportBank(@RequestBody SupportBankDTO supportBankDTO);

    @PostMapping("/money/deleteBank.json")
    boolean  deleteBank(@RequestParam("id") Integer id);
}
