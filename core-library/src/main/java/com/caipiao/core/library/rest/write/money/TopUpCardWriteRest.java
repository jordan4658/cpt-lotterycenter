package com.caipiao.core.library.rest.write.money;

import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface TopUpCardWriteRest {

    @PostMapping("/money/addTopUpCard.json")
    boolean addTopUpCard(@RequestParam("money") int money, @RequestParam("count") int count, @RequestParam("account") String account);

    @PostMapping("/money/payForTopUpCard.json")
    ResultInfo payForTopUpCard(@RequestParam("uid") int uid, @RequestParam("number") String number);
}
