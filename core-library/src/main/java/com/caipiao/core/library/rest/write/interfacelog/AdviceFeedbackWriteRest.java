package com.caipiao.core.library.rest.write.interfacelog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.appmember.AdviceFeedbackDTO;
import com.caipiao.core.library.model.ResultInfo;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AdviceFeedbackWriteRest {

    @PostMapping("/advice/pickup.json")
    boolean pickup(@RequestParam("adviceId") Integer adviceId);

    /**
     * 回复意见反馈
     * @param adviceId
     * @param reply
     * @return
     */
    @PostMapping("/advice/replyAdvice.json")
    boolean replyAdvice(@RequestParam("adviceId") Integer adviceId, @RequestParam("reply") String reply);
    
    @PostMapping("/advice/addAdviceFeedback.json")
	ResultInfo<Void> addAdviceFeedback(@RequestBody AdviceFeedbackDTO adviceFeedbackDTO);
	
}
