package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.AdviceFeedback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AdviceFeedbackRest extends BaseRest {

    /**
     * 获取意见反馈列表
     * @param pageNum
     * @param pageSize
     * @param problem
     * @param accountId
     * @param account
     * @param pickup
     * @return
     */
    @GetMapping("/advice/pageAdviceFeedback.json")
    PageResult<List<AdviceFeedback>> pageAdviceFeedback(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") String type,
                                                        @RequestParam("accountId") String accountId, @RequestParam("account") String account, @RequestParam("pickup") int pickup);

    /**
     * 获取一条意见反馈信息
     * @param adviceId
     * @return
     */
    @PostMapping("/advice/getAdviceFeedbackById.json")
    AdviceFeedback getAdviceFeedbackById(@RequestParam("adviceId") Integer adviceId);
}
