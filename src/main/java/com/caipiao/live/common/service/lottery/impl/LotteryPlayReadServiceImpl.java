package com.caipiao.live.common.service.lottery.impl;

import com.alibaba.fastjson.JSONObject;
import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.lottery.LhcPlayInfoDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayAllDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayInfoDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayOddsDTO;
import com.caipiao.live.common.model.dto.lottery.PcddPlayInfoDTO;
import com.caipiao.live.common.model.vo.lottery.BetRestrictVo;
import com.caipiao.live.common.mybatis.entity.*;
import com.caipiao.live.common.mybatis.mapper.BetRestrictMapper;
import com.caipiao.live.common.mybatis.mapper.LhcGodTypeMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayOddsMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlaySettingMapper;
import com.caipiao.live.common.mybatis.mapperext.RestrictMapperExt;
import com.caipiao.live.common.service.lottery.LotteryCategoryServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlayServiceReadSg;
import com.caipiao.live.common.service.sys.SysParamService;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LotteryPlayReadServiceImpl implements LotteryPlayServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(LotteryPlayReadServiceImpl.class);
    @Autowired
    private LotteryPlayMapper lotteryPlayMapper;
    @Autowired
    private LotteryPlaySettingMapper lotteryPlaySettingMapper;
    @Autowired
    private LotteryPlayOddsMapper lotteryPlayOddsMapper;
    @Resource
    private SysParamService sysParamService;
    @Autowired
    private LotteryMapper lotteryMapper;
    @Autowired
    private LhcGodTypeMapper lhcGodTypeMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestrictMapperExt restrictMapperExt;
    @Autowired
    private LotteryCategoryServiceReadSg lotteryCategoryService;
    @Autowired
    private BetRestrictMapper betRestrictMapper;

    @Override
    public List<LotteryPlayDTO> playList(Integer cateId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        // criteria.andCategoryIdEqualTo(cateId);//TODO 老的查询条件使用cateId
        criteria.andLotteryIdEqualTo(cateId);
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("sort desc");
        // 根据条件查询所有记录
        List<LotteryPlay> playList = lotteryPlayMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(playList)) {
            return new ArrayList<>();
        }

        // 获取分类信息
        // LotteryCategory category =
        // lotteryCategoryMapper.selectByPrimaryKey(playList.get(0).getCategoryId());
        LotteryCategory category = lotteryCategoryService.selectByCategoryId(playList.get(0).getCategoryId());
        // 递归获取子集
        return this.getPlayChildren(playList, category.getLevel(), 0);
    }

    @Override
    public LotteryPlay getPlayOneByPlayId(Integer playId) {
        LotteryPlayExample lotteryPlayExample = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = lotteryPlayExample.createCriteria();
        criteria.andPlayTagIdEqualTo(playId);
        LotteryPlay lotteryPlay = lotteryPlayMapper.selectOneByExample(lotteryPlayExample);
        return lotteryPlay;
    }

    @Override
    public List<LotteryPlay> getPlays(Integer id) {
        LotteryPlay lotteryPlay = lotteryPlayMapper.selectByPrimaryKey(id);
        String tree = lotteryPlay.getTree();
        Integer categoryId = lotteryPlay.getCategoryId();

        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andTreeNotLike(tree + "%");
        criteria.andCategoryIdEqualTo(categoryId);
        criteria.andIsDeleteEqualTo(false);
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<LotteryPlay> getPlaysByCateId(Integer cateId) {
        List<LotteryPlay> list = new ArrayList<>();
        if (cateId == null) {
            return list;
        }
        list = (List<LotteryPlay>) redisTemplate.opsForValue().get("LOTTERY_PLAY_LIST_" + cateId);

        if (CollectionUtils.isEmpty(list)) {
            // 创建查询条件
            LotteryPlayExample example = new LotteryPlayExample();
            LotteryPlayExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(cateId);
            criteria.andIsDeleteEqualTo(false);
            // 根据条件查询所有记录
            list = lotteryPlayMapper.selectByExample(example);
            redisTemplate.opsForValue().set("LOTTERY_PLAY_LIST_" + cateId, list);
        }
        return list;
    }

    @Override
    public LotteryPlay queryPlayById(Integer id) {
        return lotteryPlayMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LotteryPlay> queryPlayByParentId(Integer parentId) {
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andIsDeleteEqualTo(false);
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<LotteryPlay> getPlaysFirstLevelByCateId(Integer categoryId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        criteria.andParentIdEqualTo(0);
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("sort desc");
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<LotteryPlayDTO> getPlaysFirstLevelDtoByCateId(Integer categoryId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        criteria.andParentIdEqualTo(0);
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("lottery_id asc,sort desc");
        // 根据条件查询所有记录
        List<LotteryPlay> lotteryPlays = lotteryPlayMapper.selectByExample(example);
        List<LotteryPlayDTO> lotteryPlayDTOS = new ArrayList<>();
        for (LotteryPlay dto : lotteryPlays) {
            LotteryPlayDTO lotteryPlayDTO = new LotteryPlayDTO();
            BeanUtils.copyProperties(dto, lotteryPlayDTO);
            LotteryExample lotteryExample = new LotteryExample();
            LotteryExample.Criteria criteria1 = lotteryExample.createCriteria();
            criteria1.andLotteryIdEqualTo(dto.getLotteryId());
            Lottery lottery = lotteryMapper.selectOneByExample(lotteryExample);
            lotteryPlayDTO.setLotteryName(lottery.getName());
            lotteryPlayDTOS.add(lotteryPlayDTO);
        }
        return lotteryPlayDTOS;
    }

    /**
     * 获取子级玩法
     *
     * @param playList 父级集合
     * @return
     */
    private List<LotteryPlayDTO> getPlayChildren(List<LotteryPlay> playList, Integer level, Integer parentId) {
        List<LotteryPlayDTO> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(playList)) {
            return new ArrayList<>();
        }
        LotteryPlayDTO dto;

        // 遍历所有父级对象
        for (LotteryPlay play : playList) {
            if (!play.getParentId().equals(parentId)) {
                continue;
            }
            dto = new LotteryPlayDTO();
            BeanUtils.copyProperties(play, dto);
            // 递归获取子集
            List<LotteryPlayDTO> playChildren = this.getPlayChildren(playList, level, play.getId());
            dto.setSumLevel(level);
            dto.setChildren(playChildren);
            list.add(dto);
        }
        return list;
    }

    @Override
    public ResultInfo<Map<String, LotteryPlayInfoDTO>> queryPlayInfoById(Integer lotteryId, Integer playId) {
        Map<String, LotteryPlayInfoDTO> map = new HashMap<>();

        LotteryPlayInfoDTO dto = new LotteryPlayInfoDTO();

        // 查询玩法信息
        // LotteryPlay play = lotteryPlayMapper.selectByPrimaryKey(playId); TODO
        // 原彩种规则查询方式
        LotteryPlayExample lotteryPlayExample = new LotteryPlayExample();
        LotteryPlayExample.Criteria LotteryPlayCriteria = lotteryPlayExample.createCriteria();
        LotteryPlayCriteria.andPlayTagIdEqualTo(playId);
        LotteryPlay play = lotteryPlayMapper.selectOneByExample(lotteryPlayExample);

        if (play == null || play.getIsDelete()) {
            return ResultInfo.error("该玩法不存在或已删除！");
        }

        dto.setId(playId);
        dto.setName(play.getName());
        dto.setSection(play.getSection());

        // 查询玩法配置信息
        LotteryPlaySettingExample settingExample = new LotteryPlaySettingExample();
        LotteryPlaySettingExample.Criteria settingCriteria = settingExample.createCriteria();
        settingCriteria.andPlayIdEqualTo(playId);
        settingCriteria.andIsDeleteEqualTo(false);
        LotteryPlaySetting setting = lotteryPlaySettingMapper.selectOneByExample(settingExample);

        if (setting == null || setting.getIsDelete()) {
            return ResultInfo.error("该玩法未设置赔率信息！");
        }

        Integer settingId = setting.getId();
        dto.setSettingId(settingId);
        dto.setSetting(setting);

        // 获取赔率信息
        LotteryPlayOddsExample oddsExample = new LotteryPlayOddsExample();
        LotteryPlayOddsExample.Criteria oddsCriteria = oddsExample.createCriteria();
        oddsCriteria.andSettingIdEqualTo(settingId);
        oddsCriteria.andIsDeleteEqualTo(false);
        LotteryPlayOdds odds = lotteryPlayOddsMapper.selectOneByExample(oddsExample);

        // 判断该玩法是否设置赔率信息
        if (odds == null || odds.getIsDelete()) {
            return ResultInfo.error("该玩法未设置赔率信息！");
        }

        // 获取赔率因子
        Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryId);
        Double maxOdds = lottery.getMaxOdds();
        double divisor;
        if (maxOdds == 0D) {
            SysParameter sysParameter = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
            divisor = Double.parseDouble(sysParameter.getParamValue());
        } else {
            divisor = maxOdds;
        }

        // 获取中奖注数区间
        String winCount = odds.getWinCount();
        String totalCount = odds.getTotalCount();

        // 判断该玩法是否设置赔率信息
        if (StringUtils.isBlank(totalCount)) {
            return ResultInfo.error("该玩法未设置赔率信息！");
        }
        dto.setOdds(CaipiaoUtils.getLotteryPlayOdds(totalCount, winCount, divisor));

        map.put("play", dto);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<PcddPlayInfoDTO> queryPcddPlayOdds(Integer playId, Integer lotteryId) {
        PcddPlayInfoDTO dto = new PcddPlayInfoDTO();

        // 查询玩法信息
        LotteryPlay play = lotteryPlayMapper.selectByPrimaryKey(playId);
        // 判断该玩法是否存在
        if (play == null || play.getIsDelete()) {
            return ResultInfo.error("该玩法不存在或已删除！");
        }

        dto.setId(playId);
        dto.setName(play.getName());
        dto.setSection(play.getSection());

        // 查询玩法配置信息
        LotteryPlaySettingExample settingExample = new LotteryPlaySettingExample();
        LotteryPlaySettingExample.Criteria settingCriteria = settingExample.createCriteria();
        settingCriteria.andPlayIdEqualTo(playId);
        settingCriteria.andIsDeleteEqualTo(false);
        LotteryPlaySetting setting = lotteryPlaySettingMapper.selectOneByExample(settingExample);

        // 判断该玩法是否配置赔率信息
        if (setting == null || setting.getIsDelete()) {
            return ResultInfo.error("该玩法未设置赔率信息！");
        }

        // 获取配置id
        Integer settingId = setting.getId();
        dto.setSettingId(settingId);
        dto.setSetting(setting);

        // 获取赔率配置信息
        List<LotteryPlayOddsDTO> oddsDTOS = this.countOdds(settingId, lotteryId);

        // 判断该玩法是否配置赔率信息
        if (CollectionUtils.isEmpty(oddsDTOS)) {
            return ResultInfo.error("该玩法未设置赔率信息！");
        }

        dto.setOddsList(oddsDTOS);
        return ResultInfo.ok(dto);
    }

    @Override
    public ResultInfo<List<LhcPlayInfoDTO>> queryLhcBuy(Integer playId, Integer lotteryId) {
        List<LhcPlayInfoDTO> list = new ArrayList<>();
        // 查询子集玩法信息
        LotteryPlayExample playExample = new LotteryPlayExample();
        LotteryPlayExample.Criteria playCriteria = playExample.createCriteria();
        playCriteria.andIdEqualTo(playId);
        playCriteria.andIsDeleteEqualTo(false);
        List<LotteryPlay> plays = lotteryPlayMapper.selectByExample(playExample);
        if (CollectionUtils.isEmpty(plays)) {
            return ResultInfo.error("该玩法未配置赔率信息！");
        }

        for (LotteryPlay play : plays) {
            LhcPlayInfoDTO lhcPlayInfoDTO = new LhcPlayInfoDTO();
            BeanUtils.copyProperties(play, lhcPlayInfoDTO);

            // 查询玩法配置信息
            LotteryPlaySettingExample settingExample = new LotteryPlaySettingExample();
            LotteryPlaySettingExample.Criteria settingCriteria = settingExample.createCriteria();
            if (play.getId() == 240) {
                logger.info(JSONObject.toJSONString(play));
            }

            settingCriteria.andPlayIdEqualTo(play.getId());
            settingCriteria.andIsDeleteEqualTo(false);
            LotteryPlaySetting setting = lotteryPlaySettingMapper.selectOneByExample(settingExample);

            if (null == setting || setting.getIsDelete()) {
                return ResultInfo.error("该玩法未设置赔率信息！");
            }

            Integer settingId = setting.getId() == null ? Constants.DEFAULT_INTEGER : setting.getId();
            lhcPlayInfoDTO.setSetting(setting);

            // 计算赔率信息
            List<LotteryPlayOddsDTO> oddsDTOS = this.countOdds(settingId, lotteryId);

            // 判断该玩法是否配置赔率信息
            if (CollectionUtils.isEmpty(oddsDTOS)) {
                return ResultInfo.error("该玩法未设置赔率信息！");
            }
            // 赋值
            lhcPlayInfoDTO.setOddsList(oddsDTOS);
            list.add(lhcPlayInfoDTO);
        }

        return ResultInfo.ok(list);
    }

    /**
     * 根据配置ID获取赔率信息
     *
     * @param settingId 配置id
     * @return 如果集合为空，说明配有配置赔率信息
     */
    private List<LotteryPlayOddsDTO> countOdds(Integer settingId, Integer lotteryId) {
        // 创建赔率集合容器
        List<LotteryPlayOddsDTO> oddsDTOS = new ArrayList<>();

        // 获取赔率信息
        LotteryPlayOddsExample oddsExample = new LotteryPlayOddsExample();
        LotteryPlayOddsExample.Criteria oddsCriteria = oddsExample.createCriteria();
        oddsCriteria.andSettingIdEqualTo(settingId);
        oddsCriteria.andIsDeleteEqualTo(false);
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsMapper.selectByExample(oddsExample);

        // 判断是否设置赔率信息
        if (CollectionUtils.isEmpty(oddsList)) {
            logger.error("判断是否设置赔率信息为空！" + settingId);
            return oddsDTOS;
        }

        // 获取赔率因子
        LotteryExample lotteryExample = new LotteryExample();
        LotteryExample.Criteria criteria = lotteryExample.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        Lottery lottery = lotteryMapper.selectOneByExample(lotteryExample);

        if (null == lottery) {
            logger.error("获取赔率因子为空！" + lotteryId);
            return oddsDTOS;
        }
        Double maxOdds = lottery.getMaxOdds();
        double divisor;
        if (maxOdds == 0D) {
            SysParameter sysParameter = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
            divisor = Double.parseDouble(sysParameter.getParamValue());
        } else {
            divisor = maxOdds;
        }

        // 定义容器对象
        LotteryPlayOddsDTO oddsDTO;
        for (LotteryPlayOdds odds : oddsList) {
            oddsDTO = new LotteryPlayOddsDTO();
            // 复制属性
            BeanUtils.copyProperties(odds, oddsDTO);

            // 获取总注数/中奖注数
            String winCount = odds.getWinCount();
            String totalCount = odds.getTotalCount();

            // 判断是否设置赔率
            if (StringUtils.isBlank(winCount) || StringUtils.isBlank(totalCount)) {
                return new ArrayList<>();
            }

            // 分割字符串（总注数可能有多个，以/分隔）
            String[] totalStr = totalCount.split("/");
            // 创建字符串拼接容器
            StringBuilder sb = new StringBuilder();
            // 遍历总注数
            for (int i = 0; i < totalStr.length; i++) {
                if (i > 0) {
                    sb.append("/");
                }
                double total = Double.parseDouble(totalStr[i]);
                // 计算赔率
                double odd = total * 1.0 / Double.parseDouble(winCount) * divisor;

                BigDecimal bg = new BigDecimal(odd);
                double dd = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                sb.append(Double.toString(dd));
            }
            oddsDTO.setOdds(sb.toString());
            oddsDTOS.add(oddsDTO);
        }
        return oddsDTOS;
    }

    @Override
    public List<LotteryPlay> getPlaysByCateIdAndLevel(Integer cateId, Integer level) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cateId);
        criteria.andLevelEqualTo(level);
        criteria.andIsDeleteEqualTo(false);
        // 根据条件查询所有记录
        List<LotteryPlay> playList = lotteryPlayMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(playList)) {
            return new ArrayList<>();
        }
        return playList;
    }

    @Override
    public List<LotteryPlay> selectPlayList() {
        return this.getLotteryPlaysFromCache();
    }

    @Override
    public Map<Integer, LotteryPlay> selectPlayMap() {
        Map<Integer, LotteryPlay> map = new HashMap<>();
        // 获取所有玩法集合信息
        List<LotteryPlay> list = this.selectPlayList();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (LotteryPlay play : list) {
            map.put(play.getPlayTagId(), play);
        }
        return map;
    }

    @Override
    public LotteryPlay getParentPlaybyId(Integer lotteryId) {
        Map<Integer, LotteryPlay> map = new HashMap<>();
        // 获取所有玩法集合信息
        List<LotteryPlay> list = this.selectPlayList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (LotteryPlay play : list) {
            map.put(play.getId(), play);
        }
        LotteryPlay p = null;
        if (map.get(lotteryId) != null) {
            p = map.get(map.get(lotteryId).getParentId());
        }


        return p;
    }

    @Override
    public List<LotteryPlay> getPlaysFirstLevelByLotteryId(Integer lotteryId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        criteria.andParentIdEqualTo(0);
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("sort desc");
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<LotteryPlay> getPlaysSecondLevelByLotteryId(Integer lotteryId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        criteria.andPlayTagIdIsNotNull();
        example.setOrderByClause("play_tag_id asc");
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<LotteryPlay> getPlaysByLotteryIdAndLevel(Integer lotteryId, Integer level) {

        return null;
    }

    @Override
    public LotteryPlay selectPlayByPlayTagId(Integer playTagId) {
        if (null == playTagId || playTagId <= 0) {
            return null;
        }
        return selectPlayMap().get(playTagId);
    }

    @Override
    public PageResult<List<LhcGodType>> getWebGodType(Integer pageNo, Integer pageSize) {
        if (pageSize == null) {
            pageSize = 10;
        }

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        LhcGodTypeExample example = new LhcGodTypeExample();
        LhcGodTypeExample.Criteria criteria = example.createCriteria();
        int totalCount = lhcGodTypeMapper.countByExample(example);
        if (totalCount > 0) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
        }
        PageResult<List<LhcGodType>> pageResult = PageResult.getPageResult(pageNo, pageSize, totalCount);
        List<LhcGodType> list = lhcGodTypeMapper.selectByExample(example);
        pageResult.setData(list);

        return pageResult;
    }

    @Override
    public List<LotteryPlay> getPlaysFirstLevelByLotteryAndParentId(Integer lotteryId, Integer parentId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        criteria.andParentIdEqualTo(parentId);
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("sort desc");
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public Map<Integer, Map<Integer, Map<Integer, LotteryPlay>>> selectAllLotteryPlayByCategoryIds(
            List<Integer> categoryIds) {
        List<LotteryPlay> lotteryPlayList = getLotteryPlaysFromCache();
        if (!CollectionUtils.isEmpty(categoryIds)) {
            lotteryPlayList = lotteryPlayList.parallelStream()
                    .filter(item -> categoryIds.contains(item.getCategoryId())).collect(Collectors.toList());
        }

        Map<Integer, Map<Integer, Map<Integer, LotteryPlay>>> lotteryPlayMap = new HashMap<>();
        for (LotteryPlay lotteryPlay : lotteryPlayList) {
            Map<Integer, Map<Integer, LotteryPlay>> parentPlayMap;
            Map<Integer, LotteryPlay> playMap;
            int parentId = lotteryPlay.getParentId();
            int id = lotteryPlay.getId();
            boolean isParent = parentId == 0;
            int keyId = isParent ? id : parentId;

            if (lotteryPlayMap.containsKey(lotteryPlay.getLotteryId())) {
                parentPlayMap = lotteryPlayMap.get(lotteryPlay.getLotteryId());
            } else {
                parentPlayMap = new HashMap<>();
            }
            if (parentPlayMap.containsKey(keyId)) {
                playMap = parentPlayMap.get(keyId);
            } else {
                playMap = new HashMap<>();
            }
            playMap.put(id, lotteryPlay);
            parentPlayMap.put(keyId, playMap);
            lotteryPlayMap.put(lotteryPlay.getLotteryId(), parentPlayMap);
        }
        return lotteryPlayMap;
    }

    @Override
    public Map<Integer, List<LotteryPlayAllDTO>> selectAllLotteryPlayDTOByCategoryIds(List<Integer> categoryIds) {

        Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap = new HashMap<>();
        Map<Integer, Map<Integer, Map<Integer, LotteryPlay>>> lotteryPlayMap = this
                .selectAllLotteryPlayByCategoryIds(categoryIds);

        for (Map.Entry<Integer, Map<Integer, Map<Integer, LotteryPlay>>> entry : lotteryPlayMap.entrySet()) {
            // 彩种下的所有玩法
            List<LotteryPlayAllDTO> lotteryPlays = new ArrayList<>();
            for (Map.Entry<Integer, Map<Integer, LotteryPlay>> parent : entry.getValue().entrySet()) {
                LotteryPlayAllDTO parentDTO = null;
                List<LotteryPlayAllDTO> childrenDTOList = new ArrayList<>();
                for (Map.Entry<Integer, LotteryPlay> child : parent.getValue().entrySet()) {
                    if (parent.getKey().equals(child.getKey())) {
                        parentDTO = new LotteryPlayAllDTO();
                        BeanUtils.copyProperties(child.getValue(), parentDTO);
                        continue;
                    }
                    LotteryPlayAllDTO dto = new LotteryPlayAllDTO();
                    BeanUtils.copyProperties(child.getValue(), dto);
                    childrenDTOList.add(dto);
                }
                if (null != parentDTO) {
                    parentDTO.setPlayChildren(childrenDTOList);
                    lotteryPlays.add(parentDTO);
                }
            }
            lotteryPlayAllDTOMap.put(entry.getKey(), lotteryPlays);
        }

        return lotteryPlayAllDTOMap;
    }

    private List<LotteryPlay> getLotteryPlaysFromCache() {
        List<LotteryPlay> lotteryPlayList = (List<LotteryPlay>) redisTemplate.opsForValue()
                .get(RedisKeys.LOTTERY_PLAY_LIST_KEY);
        if (!CollectionUtils.isEmpty(lotteryPlayList)) {
            return lotteryPlayList;
        }
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        lotteryPlayList = lotteryPlayMapper.selectByExample(example);
        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_PLAY_LIST_KEY, lotteryPlayList);
        return lotteryPlayList;
    }

    @Override
    public List<LotteryPlay> getPlayByLotteryList(Integer lotteryId) {
        // 创建查询条件
        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        if (lotteryId.equals(1201)) {
            criteria.andParentIdNotEqualTo(0);
        } else {
            criteria.andParentIdEqualTo(0);
        }
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause("sort desc");
        // 根据条件查询所有记录
        return lotteryPlayMapper.selectByExample(example);
    }

    @Override
    public List<BetRestrictVo> betRestrictList(Integer lotteryId) {
        Integer number;
        if (lotteryId.equals(1201) || lotteryId.equals(1202) || lotteryId.equals(1203) || lotteryId.equals(1204)) {
            number = 0;
        } else {
            number = 1;
        }

        List<BetRestrictVo> betRestrictList = restrictMapperExt.queryRestrictList(lotteryId, number);
        return betRestrictList;
    }

    @Override
    public ResultInfo<BigDecimal> qaueryBetRestrict(Integer lotteryId) {
        BetRestrictExample betRestrictExample = new BetRestrictExample();
        BetRestrictExample.Criteria criteria = betRestrictExample.createCriteria();
        criteria.andLotteryIdEqualTo(lotteryId);
        criteria.andPlayTagIdEqualTo(0);
        BetRestrict betRestrict = betRestrictMapper.selectOneByExample(betRestrictExample);
        if (null != betRestrict) {
            return ResultInfo.ok(betRestrict.getMaxMoney());
        }
        return ResultInfo.ok(null);
    }
}
