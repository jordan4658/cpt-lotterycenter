package com.caipiao.core.library.rest.write.result;

import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/10 18:04
 */
@FeignClient(name = BUSINESS_SERVER)
public interface LotterySgWriteRest {

    /**
     * 修改开奖号码
     * @param lotteryId 彩种id
     * @param issue 期号
     * @param number 开奖号码
     * @return
     */
    @PostMapping("/lottery/changeNumber.json")
    ResultInfo<Boolean> changeNumber(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue, @RequestParam("number") String number);




}
