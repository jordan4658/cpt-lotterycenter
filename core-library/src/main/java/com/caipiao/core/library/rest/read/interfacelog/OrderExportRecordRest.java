package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.interfacelog.OrderExportRecordVO;
import com.mapper.domain.OrderExportRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface OrderExportRecordRest extends BaseRest {
    /**
     * 订单导出记录列表
     * @param pageNum
     * @param pageSize
     * @param lotteryId
     * @return
     */
    @GetMapping("/listOrderExportRecord.json")
    PageResult<List<OrderExportRecordVO>> listOrderExportRecord(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("lotteryId") String lotteryId);
}
