package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.dto.lotterymanage.LhcXsRecommendAdmireDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsRecommendAdmireWriteRest {

     
    /**
     * 根据id删除点赞
     * @param id
     * @return
     */
    @PostMapping("/lhcXs/deleteAdmireByIdAdmire.json")
    boolean deleteAdmireById(@RequestParam("id") Integer id);
    
    
    
    /**
     * 添加或修改推荐评论
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXs/addOrUpdateAdmire.json")
    boolean addOrUpdateAdmire(@RequestBody LhcXsRecommendAdmireDTO lhcXsRecommendAdmireDTO);
    /**
     * 添加
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcSg/addOrUpdateTwo.json")
    String addOrUpdateTwo(@RequestParam("memberId") Integer memberId,
                          @RequestParam("recommendId") Integer recommendId, @RequestParam("deleted") Integer deleted);
   
}
