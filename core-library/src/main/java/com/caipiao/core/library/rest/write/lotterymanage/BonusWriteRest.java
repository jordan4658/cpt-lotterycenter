package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.Bonus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface BonusWriteRest extends BaseRest {

    /**
     * 编辑投注限制内容
     * @param bonus 投注限制内容
     * @return
     */
    @PostMapping("/bonus/doEditBonus.json")
    Boolean doEditBonus(@RequestBody Bonus bonus);

}
