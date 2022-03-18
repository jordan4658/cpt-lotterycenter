package com.caipiao.core.library.rest.write.result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface BjpksLotterySgWriteRest {

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    @PostMapping("/bjpksSg/addSg.json")
    void addSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    @PostMapping("/bjpksSg/addSgCount.json")
    void addSgCount();

    /**
     * 生成北京PK10免费推荐数据
     */
    @PostMapping("/bjpksSg/addBjpksRecommend.json")
    void addBjpksRecommend();

    /**
     * 添加每期的公式杀号
     */
    @PostMapping("/bjpksSg/addKillNumber.json")
    void addKillNumber();
}
