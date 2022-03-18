package com.caipiao.core.library.rest.write.circle;

import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CircleGodPushOrderWriteRest {
    /**
     * 置顶、置地
     */
    @PostMapping("/circle/god/moveMaxPushOrderSort.json")
    boolean moveMaxPushOrderSort(@RequestParam("id") Integer id, @RequestParam("sort") Integer sort);

    /**
     * 终止/恢复跟单
     */
    @PostMapping("/circle/god/updateGodPushOrderFinishStatus.json")
    ResultInfo<Void> updateGodPushOrderFinishStatus(@RequestParam("id") Integer id, @RequestParam("finishStatus") Integer finishStatus);
}
