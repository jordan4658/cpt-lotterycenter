package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.mybatis.entity.LotteryCategory;
import com.caipiao.live.common.mybatis.entity.LotteryCategoryExample;
import com.caipiao.live.common.mybatis.mapper.LotteryCategoryMapper;
import com.caipiao.live.common.service.lottery.LotteryCategoryServiceReadSg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LotteryCategoryReadServiceImpl implements LotteryCategoryServiceReadSg {

    @Autowired
    private LotteryCategoryMapper lotteryCategoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LotteryCategory selectById(Integer id) {
        if (null == id || id <= 0) {
            return null;
        }
        List<LotteryCategory> lotteryCategoryList = getLotteryCategorysFromCache();
        if (!CollectionUtils.isEmpty(lotteryCategoryList)) {
            return lotteryCategoryList
                    .parallelStream()
                    .filter(item -> id.equals(item.getId()))
                    .findFirst()
                    .get();
        }
        return null;
    }

    @Override
    public PageResult<List<LotteryCategory>> listLotteryCategory(Integer pageNo, Integer pageSize, String type) {
        LotteryCategoryExample example = new LotteryCategoryExample();
        LotteryCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        if (StringUtils.isNotEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        example.setOrderByClause("sort desc");
        int totalCount = lotteryCategoryMapper.countByExample(example);
        PageResult<List<LotteryCategory>> pageResult = PageResult.getPageResult(pageNo, pageSize, totalCount);
        if (totalCount > 0) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
            List<LotteryCategory> list = lotteryCategoryMapper.selectByExample(example);
            pageResult.setData(list);
        }
        return pageResult;
    }

    @Override
    public List<LotteryCategory> queryAllCategory(String type) {
        List<LotteryCategory> lotteryCategoryList = getLotteryCategorysFromCache();
        if (StringUtils.isNotEmpty(type)) {
            lotteryCategoryList = lotteryCategoryList
                    .parallelStream()
                    .filter(item -> type.equals(item.getType()))
                    .collect(Collectors.toList());
        }
        return lotteryCategoryList;
    }

    @Override
    public List<LotteryCategory> queryAllCategoryIncludeDel(String type) {
        List<LotteryCategory> lotteryCategoryList = getLotteryCategorysFromCacheIncludeDel();
        if (StringUtils.isNotEmpty(type)) {
            lotteryCategoryList = lotteryCategoryList
                    .parallelStream()
                    .filter(item -> type.equals(item.getType()))
                    .collect(Collectors.toList());
        }
        return lotteryCategoryList;
    }

    @Override
    public LotteryCategory selectByCategoryId(Integer categoryId) {
        List<LotteryCategory> lotteryCategoryList = getLotteryCategorysFromCache();
        if (CollectionUtils.isEmpty(lotteryCategoryList)) {
            return null;
        }
        Map<Integer, LotteryCategory> lotteryCategoryMap = (Map<Integer, LotteryCategory>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_CATEGORY_MAP_KEY);
        LotteryCategory lotteryCategory = lotteryCategoryMap.get(categoryId);
        return null == lotteryCategory ? lotteryCategoryMap.get(String.valueOf(categoryId)) : lotteryCategory;
    }

    private List<LotteryCategory> getLotteryCategorysFromCache() {
        List<LotteryCategory> lotteryCategoryList = (List<LotteryCategory>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_CATEGORY_LIST_KEY);
        if (CollectionUtils.isEmpty(lotteryCategoryList)) {
            LotteryCategoryExample example = new LotteryCategoryExample();
            example.createCriteria().andIsDeleteEqualTo(false);
            example.setOrderByClause("sort desc");
            lotteryCategoryList = lotteryCategoryMapper.selectByExample(example);
            redisTemplate.opsForValue().set(RedisKeys.LOTTERY_CATEGORY_LIST_KEY, lotteryCategoryList);

            Map<Integer, LotteryCategory> lotteryCategoryMap = new HashMap<>();
            for (LotteryCategory category : lotteryCategoryList) {
                lotteryCategoryMap.put(category.getCategoryId(), category);
            }
            redisTemplate.opsForValue().set(RedisKeys.LOTTERY_CATEGORY_MAP_KEY, lotteryCategoryMap);
        }
        return lotteryCategoryList;
    }

    private List<LotteryCategory> getLotteryCategorysFromCacheIncludeDel() {
        LotteryCategoryExample example = new LotteryCategoryExample();
        example.setOrderByClause("sort desc");
        List<LotteryCategory> lotteryCategoryList = lotteryCategoryMapper.selectByExample(example);
        return lotteryCategoryList;
    }
}
