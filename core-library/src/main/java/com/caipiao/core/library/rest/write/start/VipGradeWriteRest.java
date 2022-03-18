package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.VipGradeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface VipGradeWriteRest {

    /**
     * 添加或修改vip等级
     * @param vipGradeDTO
     * @return
     */
    @PostMapping("/start/addOrUpdateVipGrade.json")
    boolean addOrUpdateVipGrade(@RequestBody VipGradeDTO vipGradeDTO);
}
