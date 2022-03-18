package com.caipiao.core.library.vo.circle;

public class PostCommentsVO {
    private Integer commentsId; //评论id
    private Integer commentsReplyId;//关联评论id
    private String commentsReplyNickname;//关联评论的昵称
    private String commentsContent;   // 评论内容
    private Integer commentsUid; //评论者id
    private String commentsHeads;   // 评论者头像
    private String commentsNickname;   // 评论者昵称

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

    public Integer getCommentsReplyId() {
        return commentsReplyId;
    }

    public void setCommentsReplyId(Integer commentsReplyId) {
        this.commentsReplyId = commentsReplyId;
    }

    public Integer getCommentsUid() {
        return commentsUid;
    }

    public void setCommentsUid(Integer commentsUid) {
        this.commentsUid = commentsUid;
    }

    public String getCommentsHeads() {
        return commentsHeads;
    }

    public void setCommentsHeads(String commentsHeads) {
        this.commentsHeads = commentsHeads;
    }

    public String getCommentsNickname() {
        return commentsNickname;
    }

    public void setCommentsNickname(String commentsNickname) {
        this.commentsNickname = commentsNickname;
    }

    public String getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent;
    }

    public String getCommentsReplyNickname() {
        return commentsReplyNickname;
    }

    public void setCommentsReplyNickname(String commentsReplyNickname) {
        this.commentsReplyNickname = commentsReplyNickname;
    }
}
