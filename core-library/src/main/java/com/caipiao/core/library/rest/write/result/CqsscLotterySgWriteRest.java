package com.caipiao.core.library.rest.write.result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CqsscLotterySgWriteRest {

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    @PostMapping("/cqsscSg/addSg.json")
    void addSg();

    /**
     * 生成重庆时时彩免费推荐数据
     */
    @PostMapping("/cqsscSg/addCqsscRecommend.json")
    void addCqsscRecommend();

    /**
     * 生成公司杀号
     */
    @PostMapping("/cqsscSg/addCqsscGssh.json")
    void addCqsscGssh();
}
