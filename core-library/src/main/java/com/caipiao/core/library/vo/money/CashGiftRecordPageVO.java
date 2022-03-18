package com.caipiao.core.library.vo.money;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.GiftMoneyRecord;

import java.util.List;

/**
 * 礼金记录页面VO
 *
 * @author lzy
 * @create 2018-07-17 15:08
 **/
public class CashGiftRecordPageVO {

    private CashGiftCountVO cashGiftCountVO;

    private PageResult<List<GiftMoneyRecord>> pageResult;

    public static CashGiftRecordPageVO getInstance(int pageNum, int pageSize) {
        CashGiftRecordPageVO cashGiftRecordPageVO = new CashGiftRecordPageVO();
        cashGiftRecordPageVO.setCashGiftCountVO(new CashGiftCountVO());
        cashGiftRecordPageVO.setPageResult(PageResult.getPagePesult(pageNum, pageSize, 0));
        return cashGiftRecordPageVO;
    }

    public CashGiftCountVO getCashGiftCountVO() {
        return cashGiftCountVO;
    }

    public void setCashGiftCountVO(CashGiftCountVO cashGiftCountVO) {
        this.cashGiftCountVO = cashGiftCountVO;
    }

    public PageResult<List<GiftMoneyRecord>> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<List<GiftMoneyRecord>> pageResult) {
        this.pageResult = pageResult;
    }
}
