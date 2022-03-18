package com.caipiao.core.library.rest.read.circle;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.circle.*;
import com.mapper.domain.CircleGod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CircleGodRest {

    /**
     * 申请专家
     */
    @PostMapping("/circle/god/isGod.json")
    boolean isGod(@RequestParam("uid") Integer uid);

    /**
     * 专家列表
     */
    @PostMapping("/circle/god/godList.json")
    ResultInfo<List<GodListVO>> godList(@RequestParam("uid") Integer uid, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") Integer type);

    /**
     * 专家列表Web端
     */
    @PostMapping("/circle/god/godListWeb.json")
    ResultInfo<PageResult<List<GodListWebVO>>> godListWeb(@RequestParam("uid") Integer uid, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") Integer type, @RequestParam("isMyFocus") String isMyFocus);


    /**
     * 跟单热门
     */
    @PostMapping("/circle/god/getPushOrderList.json")
    ResultInfo<List<PushOrderListVO>> getPushOrderList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("lotteryId") Integer lotteryId, @RequestParam("type") Integer type);

    /**
     * 跟单热门Web
     */
    @PostMapping("/circle/god/getPushOrderListWeb.json")
    ResultInfo<PageResult<List<PushOrderListVO>>> getPushOrderListWeb(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("lotteryId") Integer lotteryId, @RequestParam("type") Integer type);

    /**
     * 专家信息
     * @param godId
     * @return
     */
    @PostMapping("/circle/god/getGodInfo.json")
    ResultInfo<GodInfoVO> getGodInfo(@RequestParam("uid") Integer uid, @RequestParam("godId") Integer godId);

    /**
     * 专家信息Web
     * @param godId
     * @return
     */
    @PostMapping("/circle/god/getGodInfoWeb.json")
    ResultInfo<GodInfoWebVO> getGodInfoWeb(@RequestParam("uid") Integer uid, @RequestParam("godId") Integer godId);

    /**
     * 专家推单列表
     */
    @PostMapping("/circle/god/getPushOrderListByGod.json")
    ResultInfo<List<PushOrderListByGodVO>> getPushOrderListByGod(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("godId") Integer godId, @RequestParam("finishStatus") Integer finishStatus);

    /**
     * 专家推单列表WEB
     */
    @PostMapping("/circle/god/getPushOrderListByGodWeb.json")
    ResultInfo<PageResult<List<PushOrderListByGodVO>>> getPushOrderListByGodWeb(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("godId") Integer godId, @RequestParam("finishStatus") Integer finishStatus);

    /**
     * 专家数量
     */
    @PostMapping("/circle/god/godCount.json")
    Integer godCount();

    /**
     * 管理系统专家列表
     */
    @PostMapping("/circle/god/pageGod.json")
    PageResult<List<CircleGod>> pageGod(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startYll") BigDecimal startYll, @RequestParam("endYll") BigDecimal endYll, @RequestParam("startSl") BigDecimal startSl, @RequestParam("endSl") BigDecimal endSl, @RequestParam("startLz") Integer startLz, @RequestParam("endLz") Integer endLz, @RequestParam("account") String account);

    /**
     * 跟单列表
     */
    @PostMapping("/circle/god/getFollowOrders.json")
    ResultInfo<List<FollowOrderVO>> getFollowOrders(@RequestParam("userId") Integer userId, @RequestParam("orderByColumn") String orderByColumn,
                                                    @RequestParam("orderByType") String orderByType, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    /**
     * 跟单列表WEB
     */
    @PostMapping("/circle/god/getFollowOrdersWeb.json")
    ResultInfo<PageResult<List<FollowOrderVO>>> getFollowOrdersWeb(@RequestParam("userId") Integer userId, @RequestParam("orderByColumn") String orderByColumn,
                                                                   @RequestParam("orderByType") String orderByType, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 大神Ta的关注和粉丝数量
     */
    @PostMapping("/circle/god/getFansAndFocusNumberByGodId.json")
    ResultInfo<FansAndFocusNumberVO> getFansAndFocusNumberByGodId(@RequestParam("godId") Integer godId);

    /**
     * 大神Ta的关注和粉丝列表
     */
    @PostMapping("/circle/god/getFansOrFocusListByGodId.json")
    ResultInfo<PageResult<List<GodFansOrFocusListVO>>> getFansOrFocusListByGodId(@RequestParam("meId") Integer meId, @RequestParam("godId") Integer godId, @RequestParam("type") Integer type, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/circle/god/pushOrderListIndex.json")
    ResultInfo<List<PushOrderListVO>> pushOrderListIndex();
}
