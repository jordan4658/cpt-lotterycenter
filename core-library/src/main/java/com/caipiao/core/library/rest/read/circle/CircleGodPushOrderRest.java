package com.caipiao.core.library.rest.read.circle;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.circle.GdUserVO;
import com.caipiao.core.library.vo.circle.GodZhanjiVO;
import com.caipiao.core.library.vo.circle.PushOrderContentVO;
import com.mapper.domain.CircleGodPushOrder;
import com.mapper.domain.Lottery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CircleGodPushOrderRest {
    @PostMapping("/circle/god/pagePushOrder.json")
    PageResult<List<CircleGodPushOrder>> pagePushOrder(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("account") String account, @RequestParam("lotteryId") Integer lotteryId, @RequestParam("finishStatus") Integer finishStatus);

    @PostMapping("/circle/god/queryAllLottery.json")
    List<Lottery> queryAllLottery();

    @PostMapping("/circle/god/getGodZhanji.json")
    ResultInfo<GodZhanjiVO> getGodZhanji(@RequestParam("id") Integer id);

    @PostMapping("/circle/god/getPushOrderContent.json")
    ResultInfo<PushOrderContentVO> getPushOrderContent(@RequestParam("id") Integer id);

    @PostMapping("/circle/god/pageGdUsers.json")
    PageResult<List<GdUserVO>> pageGdUsers(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("pushOrderId") Integer pushOrderId);
}
