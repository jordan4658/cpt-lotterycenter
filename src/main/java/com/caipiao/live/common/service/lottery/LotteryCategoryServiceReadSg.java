package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.mybatis.entity.LotteryCategory;

import java.util.List;

public interface LotteryCategoryServiceReadSg {

    /**
     * 根据主键查询对象
     *
     * @param id 主键
     * @return
     */
    LotteryCategory selectById(Integer id);

    /**
     * 分页查询分类列表
     *
     * @param pageNo  页码
     * @param pageSize 每页显示数
     * @return
     */
    PageResult<List<LotteryCategory>> listLotteryCategory(Integer pageNo, Integer pageSize, String type);

    /**
     * 查询全部分类列表
     *
     * @return
     */
    List<LotteryCategory> queryAllCategory(String type);

    List<LotteryCategory> queryAllCategoryIncludeDel(String type);

    LotteryCategory selectByCategoryId(Integer categoryId);

}
