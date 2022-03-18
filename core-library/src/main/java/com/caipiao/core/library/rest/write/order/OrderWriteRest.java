package com.caipiao.core.library.rest.write.order;

import com.caipiao.core.library.dto.order.OrderDTO;
import com.caipiao.core.library.dto.order.OrderFollow;
import com.caipiao.core.library.model.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface OrderWriteRest {

    /**
     * 投注
     *
     * @param orderDTO 投注信息
     * @return
     */
    @PostMapping("/order/placeAnOrder.json")
    ResultInfo<Boolean> placeAnOrder(@RequestBody OrderDTO orderDTO);

    /**
     * 跟单
     *
     * @param orderFollow 跟投信息
     * @return
     */
    @PostMapping("/order/followOrder.json")
    ResultInfo<Boolean> followOrder(@RequestBody OrderFollow orderFollow);

    /**
     * 撤单
     *
     * @param orderBetId 投注id
     * @return
     */
    @PostMapping("/order/backAnOrder.json")
    ResultInfo<Boolean> backAnOrder(@RequestParam("orderBetId") Integer orderBetId, @RequestParam("userId") Integer userId);

    /**
     * 批量撤单
     *
     * @param orderBetIds 投注id集合
     * @param userId      用户id集合
     * @return
     */
    @PostMapping("/order/backOrders.json")
    ResultInfo<String> backOrders(@RequestParam("orderBetIds") List<Integer> orderBetIds, @RequestParam("userId") Integer userId);

    /**
     * 系统撤单
     *
     * @param lotteryId 彩种id
     * @param issue     期号
     * @param orderSns  订单号集合
     * @return
     */
    @PostMapping("/order/backOrderByAdmin.json")
    ResultInfo<Boolean> backOrderByAdmin(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue,
                                         @RequestParam("openNumber") String openNumber, @RequestBody List<String> orderSns);

    /**
     * 系统撤单
     *
     * @param lotteryId  彩种id
     * @param issue      期号
     * @param openNumber 开奖号码
     * @param orderSns   订单号集合
     * @return
     */
    @PostMapping("/order/reopenOrder.json")
    ResultInfo<Boolean> reopenOrder(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue,
                                    @RequestParam("openNumber") String openNumber, @RequestBody List<String> orderSns);

    /**
     * 手动开奖
     *
     * @param lotteryId  彩种id
     * @param issue      期号
     * @param openNumber 开奖号码
     * @return
     */
    @PostMapping("/order/openByHandle.json")
    ResultInfo<Boolean> openByHandle(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue, @RequestParam("openNumber") String openNumber);
}
