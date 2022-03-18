package com.caipiao.core.library.vo.betorder;

import com.caipiao.core.library.model.PageResult;

import java.util.List;

/**
 * @author lzy
 * @create 2018-07-13 11:14
 **/
public class MemberBetPageVO {

    private MemberBetCountVO memberBetCountVO;

    private  PageResult<List<MemberBetVO>> pageResult;

    public static MemberBetPageVO getInstance(int pageNum, int pageSize) {
        MemberBetPageVO memberBetPageVO = new MemberBetPageVO();
        memberBetPageVO.setMemberBetCountVO(MemberBetCountVO.getInstance( 0, 0));
        PageResult<List<MemberBetVO>> pageResult = PageResult.getPagePesult(pageNum, pageSize,0);
        memberBetPageVO.setPageResult(pageResult);
        return memberBetPageVO;
    }

    public MemberBetCountVO getMemberBetCountVO() {
        return memberBetCountVO;
    }

    public void setMemberBetCountVO(MemberBetCountVO memberBetCountVO) {
        this.memberBetCountVO = memberBetCountVO;
    }

    public  PageResult<List<MemberBetVO>> getPageResult() {
        return pageResult;
    }

    public void setPageResult( PageResult<List<MemberBetVO>> pageResult) {
        this.pageResult = pageResult;
    }
}
