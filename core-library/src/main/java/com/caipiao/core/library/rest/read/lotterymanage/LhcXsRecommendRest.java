package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.dto.lotterymanage.LhcXsReferrerDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.lotterymanage.LhcXsRecommendVO;
import com.mapper.domain.LhcXsReferrer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_READ;

@FeignClient(name = XSRDCOMMEND_READ)
public interface LhcXsRecommendRest {

    /**
     * 获取当前正在显示的心水推荐
     * @return
     */
    @GetMapping("/lhcXs/listShowRecommend.json")
    PageResult<List<LhcXsRecommendVO>> listShowRecommend(@RequestParam("id") Integer id, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "referrer") String referrer,
                                                         @RequestParam(value = "operater") String operater,
                                                         @RequestParam(value = "createTime_condition_begin") String createTime_condition_begin,
                                                         @RequestParam(value = "createTime_condition_end") String createTime_condition_end,
                                                         @RequestParam(value = "nickname") String nickname,
                                                         @RequestParam(value = "type") String type,
                                                         @RequestParam(value = "dataSources") Integer dataSources,
                                                         @RequestParam(value = "auditStatus") Integer auditStatus,
                                                         @RequestParam(value = "locked") Integer locked,
                                                         @RequestParam(value = "title") String title,
                                                         @RequestParam(value = "content") String content
    );
    
    /**
     * 获取当前正在显示的心水推荐
     * @return
     */
    @GetMapping("/lhcXs/listShowRecommendByCondition.json")
    PageResult<List<LhcXsRecommendVO>> listShowRecommendByCondition(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(value = "referrerId") Integer referrerId
    );
    
    /**
     * 根据id获取心水推荐详情
     * @param id
     * @return
     */
    @GetMapping("/lhcXs/getLhcXsRecommendById.json")
    LhcXsRecommendVO getLhcXsRecommendById(@RequestParam("id") Integer id);

    /**
     * 获取所有的六合彩心水推荐人
     * @return
     */
    @GetMapping("/lhcXs/findAllReferrer.json")
    List<LhcXsReferrer> findAllReferrer();

    /**
     * 获取心水推荐历史列表
     * @param pageNum
     * @param pageSize
     * @param referrer
     * @param operater
     * @param startTime
     * @param endTime
     * @param issue
     * @return
     */
    @GetMapping("/lhcXs/pageRecommend.json")
    PageResult<List<LhcXsRecommendVO>> pageRecommend(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("referrer") String referrer, @RequestParam("operater") String operater, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("issue") String issue);

    /**
     * APP前端获取六合彩心水推荐列表
     * @return
     */
    @GetMapping("/lhcXs/getLhcXsReCommendForApp.json")
    ResultInfo<Map<String,Object>> getLhcXsReCommendForApp(@RequestParam("isOwn") Integer isOwn, @RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer
            pageSize, @RequestParam("recommendId") Integer recommendId
            , @RequestParam("referrerId") Integer referrerId
            , @RequestParam("parentMemberId") Integer parentMemberId
            , @RequestParam("findNickNameOrTitle") String findNickNameOrTitle
            , @RequestParam("xstype") String xstype
    );
    
    /**
     * WEB前端获取六合彩心水推荐列表
     * @return
     */
    @GetMapping("/lhcXs/getLhcXsReCommendForWeb.json")
    ResultInfo<Map<String,Object>> getLhcXsReCommendForWeb(@RequestParam("isOwn") Integer isOwn, @RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer
            pageSize, @RequestParam("recommendId") Integer recommendId
            , @RequestParam("referrerId") Integer referrerId
            , @RequestParam("parentMemberId") Integer parentMemberId
            , @RequestParam("findNickNameOrTitle") String findNickNameOrTitle
            , @RequestParam("xstype") String xstype
    );
    
    /**
     * WEB前端获取六合彩心水推荐历史列表
     * @return
     */
    @GetMapping("/lhcXs/historyPageRecommend.json")
    ResultInfo<Map<String,Object>> historyPageRecommend(@RequestParam("memberId") Integer memberId,
                                                        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取后台心水推荐用户
     * @return
     */
    @GetMapping("/lhcXs/selectReferrer.json")
	List<LhcXsReferrer> selectReferrer(@RequestBody LhcXsReferrerDTO lhcXsReferrerDTO);

    
    /**
     * 获取最近的心水推荐
     * @return
     */
    @GetMapping("/lhcXs/getNextLhcXsCommendById.json")
	LhcXsRecommendVO getNextLhcXsCommendById(@RequestParam("referrerId") Integer referrerId);
    
    
	/**
	 * 随机获取头像
	 * @return
	 */
    @GetMapping("/lhcXs/getNewimg4heads.json")
	String getNewimg4heads();

	/**
	 * 获取推荐人头像
	 * @param insertMap 
	 * @return
	 */
    @GetMapping("/lhcXs/getImg4heads.json")
	String getImg4heads(@RequestParam("referrerId") Integer referrerId);
	
    
}
