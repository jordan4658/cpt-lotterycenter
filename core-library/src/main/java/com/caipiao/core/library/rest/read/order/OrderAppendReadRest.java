package com.caipiao.core.library.rest.read.order;

import com.caipiao.core.library.dto.order.OrderAppendRecordWebDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.order.OrderAppendRecordWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface OrderAppendReadRest {
    /**
     * 追号记录Web
     */
    @PostMapping("/orderAppend/orderAppendListWeb.json")
    ResultInfo<PageResult<List<OrderAppendRecordWebVO>>> orderAppendListWeb(@RequestBody OrderAppendRecordWebDTO data);
}
