package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.lotterymanage.LotteryPlaySettingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface LotteryPlaySettingReadRest {

    /**
     * 根据玩法id 查询配置
     * @param playId 玩法id
     * @return
     */
    @PostMapping("/setting/queryByPlayId.json")
    LotteryPlaySettingDTO queryByPlayId(@RequestParam(name = "playId") Integer playId);

    /**
     * 分页获取玩法配置列表
     * @param cateId 分类id
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @return
     */
    @PostMapping("/setting/querySettingListByCateId.json")
    PageResult<List<LotteryPlaySettingDTO>> querySettingListByCateId(@RequestParam("cateId") Integer cateId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

}
