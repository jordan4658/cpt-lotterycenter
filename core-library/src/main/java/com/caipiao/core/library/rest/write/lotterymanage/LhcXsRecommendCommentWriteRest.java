package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.lotterymanage.LhcXsRecommendCommentDTO;

@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsRecommendCommentWriteRest {

     
    /**
     * 根据id删除推荐评论
     * @param id
     * @return
     */
    @PostMapping("/lhcXs/deleteyId.json")
    boolean deleteById(@RequestParam("id") Integer id, @RequestParam("deleted") Integer deleted);
    
    
    
    /**
     * 添加或修改推荐评论
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXs/addOrUpdate.json")
    boolean addOrUpdate(@RequestBody LhcXsRecommendCommentDTO lhcXsRecommendCommentDTO);
    
    /**
     * 添加评论
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcSg/addLhcXsRecommendCommentDTO.json")
    String addLhcXsRecommendCommentDTO(@RequestParam("memberId") Integer memberId, @RequestParam("parentId") Integer parerntId,
                                       @RequestParam("recommendId") Integer recommendId, @RequestParam("content") String content);
    
    /**
     * 添加
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcSg/addLhcXsRecommendCommentDTOForAdmin.json")
    boolean addLhcXsRecommendCommentDTOForAdmin(@RequestParam("referrerId") Integer referrerId, @RequestParam("parentId") Integer parentId,
                                                @RequestParam("recommendId") Integer recommendId, @RequestParam("content") String content);
   
}
