package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.lotterymanage.BonusDTO;
import com.mapper.domain.Bonus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface BonusReadRest extends BaseRest {

    /**
     * 根据条件分页查询投注限制列表
     * @param categoryId 投注限制分类
     * @param cateId 彩种分类
     * @param typeId 玩法分类1
     * @param planId 玩法分类2
     * @param playId 玩法分类3
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/bonus/queryBonusList.json")
    PageResult<List<BonusDTO>> queryBonusList(@RequestParam(name = "categoryId", required = false) Integer categoryId,
                                              @RequestParam(name = "cateId", required = false) Integer cateId,
                                              @RequestParam(name = "typeId", required = false) Integer typeId,
                                              @RequestParam(name = "planId", required = false) Integer planId,
                                              @RequestParam(name = "playId", required = false) Integer playId,
                                              @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 根据id查询投注限制信息
     * @param id 投注限制id
     * @return
     */
    @PostMapping("/bonus/queryBonusById.json")
    Bonus queryBonusById(@RequestParam(name = "id") Integer id);
}
