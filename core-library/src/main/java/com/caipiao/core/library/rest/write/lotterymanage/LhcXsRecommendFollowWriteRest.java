package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.lotterymanage.LhcXsRecommendFollowDTO;
import com.caipiao.core.library.model.PageResult;

@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsRecommendFollowWriteRest {

     
    /**
     * 根据id删除推荐评论
     * @param id
     * @return
     */
    @PostMapping("/lhcXsgz/deleteyId.json")
    String deleteById(@RequestParam("memberId") Integer memberId, @RequestParam("id") Integer id, @RequestParam("referrerId") Integer referrerId, @RequestParam("recommendId") Integer recommendId);
    
    
    
    /**
     * 添加或修改推荐评论
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXsgz/addOrUpdate.json")
    boolean addOrUpdate(@RequestBody LhcXsRecommendFollowDTO lhcXsRecommendFollowDTO);
    /**
     * 添加
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXsgz/addLhcXsRecommendFollowDTO.json")
    String addLhcXsRecommendFollowDTO(@RequestParam("memberId") Integer memberId, @RequestParam("parentMemberId") Integer parentMemberId, @RequestParam("referrerId") Integer referrerId);
    
    /**
     * 添加
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXsgz/addWebLhcXsRecommendFollowDTO.json")
    String addWebLhcXsRecommendFollowDTO(@RequestParam("memberId") Integer memberId, @RequestParam("parentMemberId") Integer parentMemberId, @RequestParam("referrerId") Integer referrerId);
    
    /**
     * 根据id删除推荐评论
     * @param id
     * @return
     */
    @PostMapping("/lhcXsgz/delWebLhcXsRecommendFollowDTO.json")
    String delWebLhcXsRecommendFollowDTO(@RequestParam("memberId") Integer memberId, @RequestParam("id") Integer id, @RequestParam("referrerId") Integer referrerId, @RequestParam("recommendId") Integer recommendId);
   
    /**
     * 查询关注列表
     * @param id
     * @return
     */
    @PostMapping("/lhcXsgz/getRecommendFollowByUid.json")
    public PageResult<Set<Integer>> getRecommendFollowByUid(@RequestBody LhcXsRecommendFollowDTO lhcXsRecommendFollowDTO);
   
}
