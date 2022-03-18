package com.caipiao.core.library.vo.appmember;

import com.caipiao.core.library.model.PageResult;

import java.util.List;

/**
 * @author lzy
 * @create 2018-08-22 16:53
 **/
public class AppMemberPageVO {

    private PageResult<List<AppMemberVO>> pageResult;

    public PageResult<List<AppMemberVO>> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<List<AppMemberVO>> pageResult) {
        this.pageResult = pageResult;
    }
}
