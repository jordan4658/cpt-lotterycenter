package com.caipiao.core.library.vo.circle;

import java.util.List;

public class FansOrFocusListVO {
    private Integer number;
    private List<PostMemberVO> postMemberVOList;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<PostMemberVO> getPostMemberVOList() {
        return postMemberVOList;
    }

    public void setPostMemberVOList(List<PostMemberVO> postMemberVOList) {
        this.postMemberVOList = postMemberVOList;
    }
}
