package com.caipiao.core.library.dto.circle;

public class CommentsPostDTO {
    private Integer postId; //帖子id
    private String content; //评论内容
    private Integer commentsId;    //评论id

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }
}
