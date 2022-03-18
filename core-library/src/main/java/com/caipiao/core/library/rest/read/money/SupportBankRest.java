package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.money.SupportBankVO;
import com.mapper.domain.SupportBank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface SupportBankRest {

    @GetMapping("/money/pageSupportBank.json")
    PageResult<List<SupportBank>> pageSupportBank(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name);

    @GetMapping("/money/findSupportBankById.json")
    SupportBank findSupportBankById(@RequestParam("id") Integer id);

    /**
     * 获取所有支持绑卡的银行
     * @return
     */
    @GetMapping("/money/findAllSupportBank.json")
    List<SupportBankVO> findAllSupportBank();
}
