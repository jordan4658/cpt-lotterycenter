package com.caipiao.core.library.vo.circle;

import com.mapper.domain.CirclePost;

import java.util.List;

public class ListPostVO {
    private PostMemberVO postMember;    //发帖人信息
    private CirclePost circlePost;    //帖子信息
    private List<PostCommentsVO> postCommentsVOList;    //  评论列表

    public PostMemberVO getPostMember() {
        return postMember;
    }

    public void setPostMember(PostMemberVO postMember) {
        this.postMember = postMember;
    }

    public CirclePost getCirclePost() {
        return circlePost;
    }

    public void setCirclePost(CirclePost circlePost) {
        this.circlePost = circlePost;
    }

    public List<PostCommentsVO> getPostCommentsVOList() {
        return postCommentsVOList;
    }

    public void setPostCommentsVOList(List<PostCommentsVO> postCommentsVOList) {
        this.postCommentsVOList = postCommentsVOList;
    }
}
