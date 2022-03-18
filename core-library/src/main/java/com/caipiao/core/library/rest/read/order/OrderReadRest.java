package com.caipiao.core.library.rest.read.order;

import com.caipiao.core.library.dto.order.OrderBetDTO;
import com.caipiao.core.library.dto.order.OrderBetWebDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.order.OrderBetWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface OrderReadRest {

    /**
     * 根据条件查询投注列表信息
     * @param data 参数
     * @return
     */
    @PostMapping("/order/orderList.json")
    ResultInfo<List<OrderBetDTO>> orderList(@RequestBody OrderBetDTO data);

    /**
     * 根据条件查询投注列表信息 Web
     * @param data 参数
     * @return
     */
    @PostMapping("/order/orderListWeb.json")
    ResultInfo<PageResult<List<OrderBetWebVO>>> orderListWeb(@RequestBody OrderBetWebDTO data);

}
