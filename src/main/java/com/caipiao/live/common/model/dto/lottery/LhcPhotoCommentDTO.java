package com.caipiao.live.common.model.dto.lottery;

/**
 * ClassName:    LhcPhotoCommentDTO
 * Package:    com.caipiao.live.common.model.dto.lottery
 * Description:
 * Datetime:    2020/5/8   14:13
 * Author:   木鱼
 */
public class LhcPhotoCommentDTO {

   private String name;
   private String content;
   private String start;
   private String end;
   private Integer pageNo = 1;
   private Integer pageSize = 20;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getStart() {
      return start;
   }

   public void setStart(String start) {
      this.start = start;
   }

   public String getEnd() {
      return end;
   }

   public void setEnd(String end) {
      this.end = end;
   }

   public Integer getPageNo() {
      return pageNo;
   }

   public void setPageNo(Integer pageNo) {
      this.pageNo = pageNo;
   }

   public Integer getPageSize() {
      return pageSize;
   }

   public void setPageSize(Integer pageSize) {
      this.pageSize = pageSize;
   }
}
