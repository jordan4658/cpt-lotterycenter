package com.caipiao.core.library.dto.appmember;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.appLogin.MemberVO;
import com.mapper.domain.AppMember;

public class ErrorPwdDTO {
    private ResultInfo<MemberVO> resultInfo;
    private AppMember user;
    private Integer type;   //1登录密码；2资金密码

    public ResultInfo<MemberVO> getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo<MemberVO> resultInfo) {
        this.resultInfo = resultInfo;
    }

    public AppMember getUser() {
        return user;
    }

    public void setUser(AppMember user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
