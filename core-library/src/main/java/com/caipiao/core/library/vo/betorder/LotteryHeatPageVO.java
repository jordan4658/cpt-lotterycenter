package com.caipiao.core.library.vo.betorder;

import com.caipiao.core.library.model.PageResult;

import java.util.List;

/**
 * 彩种热度页面VO
 *
 * @author lzy
 * @create 2018-07-13 16:00
 **/
public class LotteryHeatPageVO {

    private LotteryHeatCountVO lotteryHeatCountVO;

    private PageResult<List<LotteryHeatVO>> pageResult;

    public static LotteryHeatPageVO getInstance(int pageNum, int pageSize) {
        LotteryHeatPageVO lotteryHeatPageVO = new LotteryHeatPageVO();
        PageResult<List<LotteryHeatVO>> pageResult = PageResult.getPagePesult(pageNum, pageSize,0);
        lotteryHeatPageVO.setPageResult(pageResult);
        return lotteryHeatPageVO;
    }

    public LotteryHeatCountVO getLotteryHeatCountVO() {
        return lotteryHeatCountVO;
    }

    public void setLotteryHeatCountVO(LotteryHeatCountVO lotteryHeatCountVO) {
        this.lotteryHeatCountVO = lotteryHeatCountVO;
    }

    public PageResult<List<LotteryHeatVO>> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<List<LotteryHeatVO>> pageResult) {
        this.pageResult = pageResult;
    }
}
