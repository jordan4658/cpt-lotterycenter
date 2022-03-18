package com.caipiao.core.library.rest.write.order;

import com.caipiao.core.library.dto.order.AppendDTO;
import com.caipiao.core.library.dto.order.OrderAppendDTO;
import com.caipiao.core.library.dto.order.OrderBetDTO;
import com.caipiao.core.library.dto.order.OrderPlayDTO;
import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface OrderAppendWriteRest {

    /**
     * 生成最好计划
     * @param appendDTO 追号计划信息
     * @return
     */
    @PostMapping("/orderAppend/orderAppendPlan.json")
    ResultInfo<List<OrderPlayDTO>> orderAppendPlan(@RequestBody AppendDTO appendDTO);

    /**
     * 追号投注
     * @param appendDTO 追号计划信息
     * @return
     */
    @PostMapping("/orderAppend/orderAppend.json")
    ResultInfo<Boolean> orderAppend(@RequestBody AppendDTO appendDTO);

    /**
     * 追号记录
     * @param data 参数
     * @return
     */
    @PostMapping("/orderAppend/orderAppendList.json")
    ResultInfo<List<OrderAppendDTO>> orderAppendList(@RequestBody OrderBetDTO data);

}
