package com.caipiao.core.library.rest.read.appmember;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.appmember.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * 会员有关资金查询的接口
 */
@FeignClient(name = BUSINESS_READ)
public interface MemberFundRest {

    /**
     * 会员的提现记录
     * @param memberId 会员id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/memberFund/pageWithdrawDeposit.json")
    ResultInfo<List<WithdrawDepositVO>> pageWithdrawDeposit(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 会员的充值记录
     * @param memberId 会员id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/memberFund/pageTopUp.json")
    ResultInfo<List<TopUpVO>> pageTopUp(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 会员的活动记录
     * @param memberId 会员id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/memberFund/pageActivity.json")
    ResultInfo<List<MemberActivityVO>> pageActivity(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 会员的帐变记录
     * @param memberId 会员id
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param types 帐变类型的集合
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @PostMapping("/memberFund/pageBalanceChange.json")
    ResultInfo<List<MemberBalanceChangeVO>> pageBalanceChange(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestBody List<Integer> types, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 会员的个人报表
     * @param memberId 会员id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @PostMapping("/memberFund/reportForms.json")
    ResultInfo<ReportFormsVO> reportForms(@RequestParam("memberId") Integer memberId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @PostMapping("/memberFund/winnersRanking.json")
    ResultInfo<List<WinnersRankingVO>> winnersRanking();

    @PostMapping("/memberFund/pageBalanceChangeWeb.json")
    ResultInfo<PageResult<List<MemberBalanceChangeVO>>> pageBalanceChangeWeb(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestBody List<Integer> types, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @PostMapping("/memberFund/pageFaDianRecordChangeWeb.json")
    ResultInfo<PageResult<List<MemberBalanceChangeVO>>> pageFaDianRecordChangeWeb(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestBody List<Integer> types, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);
}
