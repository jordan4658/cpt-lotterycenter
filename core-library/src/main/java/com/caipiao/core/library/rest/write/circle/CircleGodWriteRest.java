package com.caipiao.core.library.rest.write.circle;

import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.CircleGod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CircleGodWriteRest {
    /**
     * 推单
     */
    @PostMapping("/circle/god/pushOrder.json")
    ResultInfo<Boolean> pushOrder(@RequestParam("uid") Integer uid, @RequestParam("orderBetIds") Integer[] orderBetIds, @RequestParam("secretStatus") Integer secretStatus, @RequestParam("ensureOdds") BigDecimal ensureOdds, @RequestParam("bonusScale") BigDecimal bonusScale, @RequestParam("godAnalyze") String godAnalyze);

    /**
     * 关注/取消关注
     */
    @PostMapping("/circle/god/focusOrCancle.json")
    ResultInfo<Boolean> focusOrCancle(@RequestParam("meId") Integer meId, @RequestParam(name = "otherId") Integer otherId, @RequestParam(name = "type") Integer type);

    /**
     * 修改大神
     */
    @PostMapping("/circle/god/updateGod.json")
    ResultInfo<Void> updateGod(@RequestBody CircleGod circleGod);

    /**
     * 禁止/恢复推单
     */
    @PostMapping("/circle/god/updateGodPushOrderStatus.json")
    ResultInfo<Void> updateGodPushOrderStatus(@RequestParam("id") Integer id, @RequestParam("pushOrderStatus") Integer pushOrderStatus);
}
