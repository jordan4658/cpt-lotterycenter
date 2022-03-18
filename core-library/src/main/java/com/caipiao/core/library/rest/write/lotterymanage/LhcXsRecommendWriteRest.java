package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_SERVER;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.lotterymanage.LhcXsRecommendDTO;
import com.caipiao.core.library.dto.lotterymanage.LhcXsReferrerDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.lotterymanage.LhcXsRecommendAppVO;

@FeignClient(name = XSRDCOMMEND_SERVER)
public interface LhcXsRecommendWriteRest {

    /**
     * 添加推荐人
     * @param lhcXsReferrerDTO
     * @return
     */
    @PostMapping("/lhcXs/addReferrer.json")
    boolean addReferrer(@RequestBody LhcXsReferrerDTO lhcXsReferrerDTO);

    /**
     * 添加或修改心水推荐
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXs/addOrUpdateRecommend.json")
    boolean addOrUpdateRecommend(@RequestBody LhcXsRecommendDTO lhcXsRecommendDTO);
    
    
    /**
     * APP端
     * 添加或修改心水推荐
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXs/addOrUpdateRecommendForApp.json")
    ResultInfo<String> addOrUpdateRecommendForApp(@RequestBody LhcXsRecommendDTO lhcXsRecommendDTO);

    /**
     * 移动心水推荐的排序,上移或下移
     * @param id
     * @param moveId
     * @return
     */
    @PostMapping("/lhcXs/moveRecommendSort.json")
    boolean moveRecommendSort(@RequestParam("id") Integer id, @RequestParam("moveId") Integer moveId);

    /**
     * 移动心水推荐的排序,置顶或置底
     * @param id
     * @param sort
     * @return
     */
    @PostMapping("/lhcXs/moveMaxRecommendSort.json")
    boolean moveMaxRecommendSort(@RequestParam("id") Integer id, @RequestParam("sort") Integer sort);

    /**
     * 根据id删除心水推荐
     * @param id
     * @return
     */
    @PostMapping("/lhcXs/deleteXsRecommendById.json")
    boolean deleteXsRecommendById(@RequestParam("id") Integer id);

    /**
     * 修改心水推荐基本信息
     * @param lhcXsRecommendDTO
     * @return
     */
    @PostMapping("/lhcXs/updateRecommend.json")
    boolean updateRecommend(@RequestBody LhcXsRecommendDTO lhcXsRecommendDTO);

    /**
     * 获取心水推荐详情,并添加一次阅读数
     * @param id
     * @return
     */
    @PostMapping("/lhcXs/getLhcXsReCommendAppVOById.json")
    ResultInfo<LhcXsRecommendAppVO> getLhcXsReCommendAppVOById(@RequestParam("id") Integer id, @RequestParam("memberId") Integer memberId);
    
    /**
     * 获取心水推荐详情,并添加一次阅读数
     * @param id
     * @return
     */
    @PostMapping("/lhcXs/getNextLhcXsCommendById.json")
    ResultInfo<LhcXsRecommendAppVO> getNextLhcXsCommendById(@RequestParam("memberId") Integer memberId);
    
    
    
    
    /**
     * 锁所有帖子
     * @return
     */
    @PostMapping("/lhcXs/lockAll.json")
	boolean lockAll();
    
    
    /**
     * 锁所有帖子
     * @return
     */
    @PostMapping("/lhcXs/lockXsBycondition.json")
	boolean lockXsBycondition(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

	/**
	 * 锁贴
	 * @param id
	 * @return
	 */
    @PostMapping("/lhcXs/lockXs.json")
	boolean lockXs(@RequestParam("id") Integer id, @RequestParam("locked") Integer locked);

    
    /**
	 * 审核
	 * @param id
	 * @return
	 */
    @PostMapping("/lhcXs/auditXs.json")
	boolean auditXs(@RequestParam("id") Integer id);
    
    /**
   	 * APP新增心水推荐
   	 * @param id
   	 * @return
   	 */
    @PostMapping("/lhcXs/addRecommend_app.json")
    ResultInfo<String> addRecommend_app(@RequestBody LhcXsRecommendDTO lhcXsRecommendDTO);

    /**
   	 * APP修改心水推荐
   	 * @param id
   	 * @return
   	 */
    @PostMapping("/lhcXs/updateRecommendByAPP.json")
	String updateRecommend_app(@RequestBody LhcXsRecommendDTO lhcXsRecommendDTO);
	
}
