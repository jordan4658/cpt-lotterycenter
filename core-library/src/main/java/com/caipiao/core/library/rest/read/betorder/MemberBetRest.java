package com.caipiao.core.library.rest.read.betorder;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.betorder.LotteryHeatCountVO;
import com.caipiao.core.library.vo.betorder.MemberBetPageVO;
import com.caipiao.core.library.vo.betorder.ProfitAndLossCountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface MemberBetRest {

    /**
     * 注单的盈亏统计
     *
     * @return
     */
    @GetMapping("/betorder/getProfitAndLossCountVO.json")
    ProfitAndLossCountVO getProfitAndLossCountVO(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

    /**
     * 后台注单-投注管理
     *
     * @param lotteryId 彩种id
     * @return
     */
    @GetMapping("/betorder/getOrderBetPageVO.json")
    MemberBetPageVO getOrderBetPageVO(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("cateId") Integer cateId,
                                      @RequestParam("typeId") Integer typeId, @RequestParam("planId") Integer planId, @RequestParam("playId") Integer playId,
                                      @RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue, @RequestParam("orderCode") String orderCode,
                                      @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("memberId") String memberId,
                                      @RequestParam("account") String account, @RequestParam("status") String status);

    /**
     * 后台-彩种热度
     *
     * @param lotteryId 彩种id
     * @return
     */
    @GetMapping("/betorder/lotteryHeat.json")
    List<LotteryHeatCountVO> lotteryHeat(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("cateId") Integer cateId, @RequestParam("typeId") Integer typeId, @RequestParam("planId") Integer planId,
                                         @RequestParam("playId") Integer playId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @GetMapping("/betorder/getdllist.json")
    PageResult<List<HashMap<String, Object>>> getdllist(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("id") String id,
                                                        @RequestParam("account") String account, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @GetMapping("/betorder/getdllisttree.json")
    List<HashMap<String, Object>> getdllisttree(@RequestParam("account") String account);
}
