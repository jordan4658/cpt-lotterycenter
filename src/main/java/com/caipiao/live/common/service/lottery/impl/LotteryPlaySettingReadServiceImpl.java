package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.dto.lottery.LotteryPlaySettingDTO;
import com.caipiao.live.common.mybatis.entity.LotteryCategory;
import com.caipiao.live.common.mybatis.entity.LotteryPlay;
import com.caipiao.live.common.mybatis.entity.LotteryPlayExample;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOddsExample;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySettingExample;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayOddsMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlaySettingMapper;
import com.caipiao.live.common.service.lottery.LotteryCategoryServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlaySettingServiceReadSg;
import com.caipiao.live.common.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LotteryPlaySettingReadServiceImpl implements LotteryPlaySettingServiceReadSg {

    @Autowired
    private LotteryPlaySettingMapper lotteryPlaySettingMapper;
    @Autowired
    private LotteryPlayOddsMapper lotteryPlayOddsMapper;
    @Autowired
    private LotteryPlayMapper lotteryPlayMapper;
    @Autowired
    private LotteryCategoryServiceReadSg lotteryCategoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LotteryPlaySettingDTO queryByPlayId(Integer playId) {
        LotteryPlaySettingDTO dto = new LotteryPlaySettingDTO();

        // 查询配置信息
        LotteryPlaySettingExample settingExample = new LotteryPlaySettingExample();
        LotteryPlaySettingExample.Criteria criteria = settingExample.createCriteria();
        criteria.andPlayIdEqualTo(playId);
        criteria.andIsDeleteEqualTo(false);
        LotteryPlaySetting lotteryPlaySetting = lotteryPlaySettingMapper.selectOneByExample(settingExample);
        // 判空
        if (lotteryPlaySetting == null) {
            return dto;
        }
        // 赋值配置信息到dto
        BeanUtils.copyProperties(lotteryPlaySetting, dto);
        // 获取赔率信息
        LotteryPlayOddsExample oddsExample = new LotteryPlayOddsExample();
        LotteryPlayOddsExample.Criteria oddsCriteria = oddsExample.createCriteria();
        oddsCriteria.andSettingIdEqualTo(lotteryPlaySetting.getId());
        oddsCriteria.andIsDeleteEqualTo(false);
        List<LotteryPlayOdds> lotteryPlayOdds = lotteryPlayOddsMapper.selectByExample(oddsExample);
        dto.setOddsList(lotteryPlayOdds);
        return dto;
    }

    @Override
    public PageResult<List<LotteryPlaySettingDTO>> querySettingListByCateId(Integer cateId, Integer pageNo, Integer pageSize) {
        // 查询所有分类信息
        //LotteryCategory category = lotteryCategoryMapper.selectByPrimaryKey(cateId);
        LotteryCategory category = lotteryCategoryService.selectByCategoryId(cateId);
        Integer level = category.getLevel();
        // 查询所有分类信息
        LotteryPlayExample playExample = new LotteryPlayExample();
        LotteryPlayExample.Criteria playCriteria = playExample.createCriteria();
        playCriteria.andCategoryIdEqualTo(cateId);
        playCriteria.andIsDeleteEqualTo(false);
        List<LotteryPlay> plays = lotteryPlayMapper.selectByExample(playExample);
        Map<Integer, LotteryPlay> playMap = new HashMap<>();
        List<Integer> values = new ArrayList<Integer>();
        for (LotteryPlay play : plays) {
            playMap.put(play.getId(), play);
            values.add(play.getId());
        }

        LotteryPlaySettingExample example = new LotteryPlaySettingExample();
        LotteryPlaySettingExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        if (cateId != null && cateId != 0) {
            criteria.andCateIdEqualTo(cateId);
        }

        criteria.andPlayIdIn(values);

        int count = lotteryPlaySettingMapper.countByExample(example);

        PageResult<List<LotteryPlaySettingDTO>> pageResult = PageResult.getPageResult(pageNo, pageSize, count);
        if (count > 0) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
            List<LotteryPlaySetting> list = lotteryPlaySettingMapper.selectByExample(example);
            List<LotteryPlaySettingDTO> dtos = new ArrayList<>();
            LotteryPlay play3, play2, play1;
            for (LotteryPlaySetting setting : list) {
                LotteryPlaySettingDTO dto = new LotteryPlaySettingDTO();
                BeanUtils.copyProperties(setting, dto);
                dto.setCateName(category.getName());
                if (level > 2) {
                    play3 = playMap.get(setting.getPlayId());
                    play2 = playMap.get(play3.getParentId());
                    play1 = playMap.get(play2.getParentId());
                    dto.setType(play1.getName());
                    dto.setPlan(play2.getName());
                    dto.setPlanName(play3.getName());
                } else if (level > 1) {
                    play2 = playMap.get(setting.getPlayId());


                    if (play2 != null) {
                        play1 = playMap.get(play2.getParentId());
                        dto.setPlanName(play2.getName());
                        if (play1 != null) {
                            dto.setPlan(play1.getName());

                        }
                    }
                } else {
                    dto.setPlanName(playMap.get(setting.getPlayId()).getName());
                }
                dtos.add(dto);
            }
            pageResult.setData(dtos);
        }
        return pageResult;
    }

    @Override
    public LotteryPlaySetting queryLotteryPlaySettingForPlayId(Integer playId, Integer playTagId) {
        // 查询配置信息
        LotteryPlaySettingExample settingExample = new LotteryPlaySettingExample();
        LotteryPlaySettingExample.Criteria criteria = settingExample.createCriteria();
        criteria.andPlayIdEqualTo(playId);
        criteria.andPlayTagIdEqualTo(playTagId);
        criteria.andIsDeleteEqualTo(false);
        LotteryPlaySetting lotteryPlaySetting = lotteryPlaySettingMapper.selectOneByExample(settingExample);
        return lotteryPlaySetting;
    }

    @Override
    public List<LotteryPlaySetting> queryLotteryPlaySetting(List<Integer> playIdList) {
        List<LotteryPlaySetting> lotteryPlaySettingList = getLotteryPlaySettingsFromCache();
        if (!CollectionUtils.isEmpty(playIdList)) {
            Map playIdMap = CollectionUtil.convertToMap(playIdList);
            lotteryPlaySettingList = lotteryPlaySettingList
                    .parallelStream()
                    .filter(item -> playIdMap.containsKey(item.getPlayId()))
                    .collect(Collectors.toList());
        }
        return lotteryPlaySettingList;
    }

    @Override
    public Map<String, LotteryPlaySetting> queryLotteryPlaySettingMap(List<Integer> playIdList) {
        Map<String, LotteryPlaySetting> map = new HashMap<>();
        List<LotteryPlaySetting> lotteryPlaySettings = this.queryLotteryPlaySetting(playIdList);
        for (LotteryPlaySetting setting : lotteryPlaySettings) {
            if (null != setting.getPlayTagId()) {
                map.put(String.valueOf(setting.getPlayTagId()), setting);
            }
        }
        return map;
    }

    private List<LotteryPlaySetting> getLotteryPlaySettingsFromCache() {



        List<LotteryPlaySetting> lotteryPlaySettingList = (List<LotteryPlaySetting>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA);
        if (!CollectionUtils.isEmpty(lotteryPlaySettingList)) {
            return lotteryPlaySettingList;
        }
        LotteryPlaySettingExample example = new LotteryPlaySettingExample();
        example.createCriteria().andIsDeleteEqualTo(false);
        lotteryPlaySettingList = lotteryPlaySettingMapper.selectByExample(example);
        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA, lotteryPlaySettingList);
        return lotteryPlaySettingList;
    }
}
