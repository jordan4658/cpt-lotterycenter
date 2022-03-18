package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.mybatis.entity.LotteryFavorite;
import com.caipiao.live.common.mybatis.entity.LotteryFavoriteExample;
import com.caipiao.live.common.mybatis.mapper.LotteryFavoriteMapper;
import com.caipiao.live.common.service.lottery.LotteryFavoriteReadServiceReadSg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class LotteryFavoriteReadSgServiceImpl implements LotteryFavoriteReadServiceReadSg {

    @Autowired
    private LotteryFavoriteMapper lotteryFavoriteMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<LotteryFavorite> queryByUserId(int userId) {

        List<LotteryFavorite> lotteryFavorites = (List<LotteryFavorite>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_USER_PREFIX + userId);
        if (null != lotteryFavorites && lotteryFavorites.size() > 0) {
            return lotteryFavorites;
        }

        //DB拉取
        LotteryFavoriteExample example = new LotteryFavoriteExample();
        example.createCriteria().andUserIdEqualTo(userId);
        lotteryFavorites = lotteryFavoriteMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(lotteryFavorites)) {
            redisTemplate.opsForValue().set(RedisKeys.LOTTERY_FAVORITE_USER_PREFIX + userId, lotteryFavorites);
            return lotteryFavorites;
        }

        //获取默认收藏彩票 LOTTERY_FAVORITE_DEFAULT
        lotteryFavorites = (List<LotteryFavorite>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_DEFAULT);
        if (null == lotteryFavorites || lotteryFavorites.size() == 0) {
            lotteryFavorites = this.queryDefaultLotteryFavorite();
        }

        return lotteryFavorites;
    }

    /**
     * 查询默认用户收藏彩票
     *
     * @return
     */
    public List<LotteryFavorite> queryDefaultLotteryFavorite() {

        List<LotteryFavorite> lotteryFavorites = (List<LotteryFavorite>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_DEFAULT);
        if (!CollectionUtils.isEmpty(lotteryFavorites)) {
            return lotteryFavorites;
        }

        LotteryFavoriteExample example = new LotteryFavoriteExample();
        //userId=0 为用户默认收藏的彩票
        example.createCriteria().andUserIdEqualTo(0);
        lotteryFavorites = lotteryFavoriteMapper.selectByExample(example);

        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_FAVORITE_DEFAULT, lotteryFavorites);

        return lotteryFavorites;
    }
}
