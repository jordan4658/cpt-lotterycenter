package com.caipiao.core.library.rest.write.circle;

import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CircleGodApplyWriteRest {
    /**
     * 申请专家
     */
    @PostMapping("/circle/god/applyGod.json")
    ResultInfo<Boolean> applyGod(@RequestParam("uid") Integer uid, @RequestParam("content") String content, @RequestParam("qq") String qq);

    /**
     * 通过、拒绝、取消大神
     */
    @PostMapping("/circle/god/handleGod.json")
    boolean handleGod(@RequestParam("type") Integer type, @RequestParam("id") Integer id, @RequestParam("admin") String admin);

}
