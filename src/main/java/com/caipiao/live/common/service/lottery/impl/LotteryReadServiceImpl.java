package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.enums.lottery.LotteryCategoryEnum;
import com.caipiao.live.common.enums.lottery.LotteryTypeEnum;
import com.caipiao.live.common.model.LotterySet;
import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.lottery.HotFavoriteDTO;
import com.caipiao.live.common.model.dto.lottery.LhcPlayInfoDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryAllDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryFavoriteDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryInfo;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayAllDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayAllOdds;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlaySettingDTO;
import com.caipiao.live.common.model.dto.lottery.LotterySgModel;
import com.caipiao.live.common.model.dto.result.BjpksLiangMian;
import com.caipiao.live.common.model.dto.result.SscMissNumDTO;
import com.caipiao.live.common.model.vo.lottery.OptionSelectVo;
import com.caipiao.live.common.mybatis.entity.*;
import com.caipiao.live.common.mybatis.mapper.BjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.CqsscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryFavoriteMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayOddsMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlaySettingMapper;
import com.caipiao.live.common.mybatis.mapper.PceggLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.TxffcLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.XjsscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapper.XyftLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.LotteryBeanMapper;
import com.caipiao.live.common.mybatis.mapperext.lottery.LotteryMapperExt;
import com.caipiao.live.common.mybatis.mapperext.sg.CqsscLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.LhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryCategoryServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryFavoriteReadServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlayOddsServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlayServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlaySettingServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryServiceReadSg;
import com.caipiao.live.common.service.sys.SysParamService;
import com.caipiao.live.common.util.CollectionUtil;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.lottery.BjpksUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.CqsscSgUtils;
import com.caipiao.live.common.util.lottery.NextIssueTimeUtil;
import com.caipiao.live.common.util.lottery.TxffcSgUtils;
import com.caipiao.live.common.util.lottery.XjsscSgUtils;
import com.caipiao.live.common.util.lottery.XyftUtils;
import com.caipiao.live.common.util.redis.RedisBusinessUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryReadServiceImpl implements LotteryServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(LotteryReadServiceImpl.class);
    @Autowired
    private LotteryMapper lotteryMapper;
    @Autowired
    private CqsscLotterySgMapperExt cqsscLotterySgMapperExt;
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private LhcLotterySgServiceReadSg lhcLotterySgService;
    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
    @Autowired
    private LotteryBeanMapper lotteryBeanMapper;
    @Autowired
    private LotteryFavoriteMapper lotteryFavoriteMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LotteryCategoryServiceReadSg lotteryCategoryService;
    @Autowired
    private LotteryPlayServiceReadSg lotteryPlayService;
    @Autowired
    private LotteryPlayMapper lotteryPlayMapper;
    @Autowired
    private LotteryPlaySettingMapper lotteryPlaySettingMapper;
    @Autowired
    private LotteryPlaySettingServiceReadSg lotteryPlaySettingService;
    @Autowired
    private LotteryPlayOddsMapper lotteryPlayOddsMapper;
    @Autowired
    private LotteryPlayOddsServiceReadSg lotteryPlayOddsService;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private LotteryMapperExt lotteryMapperExt;
    @Autowired
    private LotteryFavoriteReadServiceReadSg lotteryFavoriteReadService;

    @Override
    public Lottery selectLotteryById(Integer lotteryPrimaryId) {
        if (null == lotteryPrimaryId || lotteryPrimaryId <= 0) {
            return null;
        }
        this.queryAllLotteryFromCache(true, RedisKeys.LOTTERY_ALL_LIST_KEY, RedisKeys.LOTTERY_ALL_MAP_KEY);
        Map<Integer, Lottery> lotteryMap = (Map<Integer, Lottery>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_ALL_MAP_KEY);
        return lotteryMap.get(String.valueOf(lotteryPrimaryId));
    }

    @Override
    public List<Lottery> selectLotteryList(String categoryType) {
        return this.queryLotteryByCategoryType(categoryType);
    }

    @Override
    public Map<Integer, Lottery> selectLotteryMap(String categoryType) {
        Map<Integer, Lottery> map = new HashMap<>();
        List<Lottery> lotteries = this.selectLotteryList(categoryType);
        if (CollectionUtils.isEmpty(lotteries)) {
            return map;
        }
        for (Lottery lottery : lotteries) {
            map.put(lottery.getLotteryId(), lottery);
        }
        return map;
    }

    @Override
    public PageResult<List<LotteryDTO>> queryAllLotteryList(Integer cateId, String name, Integer pageNo, Integer pageSize, String type) {
        // 查询所有分类信息
        List<LotteryCategory> lotteries = lotteryCategoryService.queryAllCategoryIncludeDel(type);
        Map<Integer, LotteryCategory> map = new HashMap<>();
        for (LotteryCategory cate : lotteries) {
            map.put(cate.getCategoryId(), cate);
        }

        LotteryExample example = new LotteryExample();
        LotteryExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (cateId != null && cateId != 0) {
            criteria.andCategoryIdEqualTo(cateId);
        }
        if (StringUtils.isNotEmpty(type)) {
            List<Integer> ids = LotteryCategoryEnum.getLotteryCategoryIdsByType(lotteries, type);
            if (!CollectionUtils.isEmpty(ids)) {
                criteria.andCategoryIdIn(ids);
            }
        }
        int count = lotteryMapper.countByExample(example);

        PageResult<List<LotteryDTO>> pageResult = PageResult.getPageResult(pageNo, pageSize, count);
        if (count > 0) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
            example.setOrderByClause("lottery_id asc");
            List<Lottery> list = lotteryMapper.selectByExample(example);
            List<LotteryDTO> dtos = new ArrayList<>();
            for (Lottery lottery : list) {
                LotteryDTO dto = new LotteryDTO();
                BeanUtils.copyProperties(lottery, dto);
                dto.setCateName(map.get(lottery.getCategoryId()).getName());
                dtos.add(dto);
            }
            pageResult.setData(dtos);
        }
        return pageResult;
    }

    @Override
    public List<Lottery> queryLotteryByCategoryType(String type) {
        logger.info("queryLotteryByCategoryType by type:{}.", type);
        List<Lottery> lotteries = queryAllLotteryFromCache();
        final List<Integer> categoryIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            List<LotteryCategory> categories = lotteryCategoryService.queryAllCategory(type);
            categoryIdsList.addAll(categories.stream().map(item -> item.getCategoryId()).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(lotteries) && !CollectionUtils.isEmpty(categoryIdsList)) {
            lotteries = lotteries
                    .stream()
                    .filter(item -> categoryIdsList.contains(item.getCategoryId()))
                    .collect(Collectors.toList());
        }
        return CollectionUtils.isEmpty(lotteries) ? new ArrayList<Lottery>() : lotteries;
    }

    @Override
    public List<Lottery> queryLotteryByCategoryId(Integer categoryId) {
        logger.info("queryLotteryByCategoryId by categoryId:{}.", categoryId);
        if (null == categoryId || categoryId <= 0) {
            return null;
        }
        List<Lottery> lotteries = queryAllLotteryFromCache();
        if (!CollectionUtils.isEmpty(lotteries)) {
            lotteries = lotteries
                    .stream()
                    .filter(item -> categoryId.equals(item.getCategoryId()))
                    .collect(Collectors.toList());
        }
        return lotteries;
    }

    @Override
    public List<Map<String, Object>> queryAllList() {
        return queryInternalList(null);
    }

    @Override
    public List<Map<String, Object>> queryInternalList() {
        List<Map<String, Object>> list = RedisBusinessUtil.getLotteryAllInnerList();
        if (CollectionUtil.isEmpty(list)) {
            list = queryInternalList(LotteryTypeEnum.LOTTERY.name());
            RedisBusinessUtil.addLotteryAllInfo(list);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryInternalList(String type) {
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询所有彩种
        List<Lottery> lotteries = queryLotteryByCategoryType(type);
        List<LotteryCategory> categories = lotteryCategoryService.queryAllCategory(type);
        Map<String, LotteryCategory> categoryMap = new HashMap<>();
        for (LotteryCategory category : categories) {
            categoryMap.put(category.getCategoryId().toString(), category);
        }
        for (LotteryCategory cate : categories) {
            Map<String, Object> lotcage = new HashMap<>();
            lotcage.put("id", cate.getId());
            lotcage.put("name", cate.getName());
            lotcage.put("categoryId", cate.getCategoryId());
            lotcage.put("cateName", cate.getName());
            lotcage.put("intro", cate.getAlias());
            lotcage.put("isWork", cate.getIsWork());
            lotcage.put("sort", cate.getSort());

            List<Map<String, Object>> lotlist = new ArrayList<>();
            for (Lottery lottery : lotteries) {
                Map<String, Object> lot = new HashMap<>();
                // 获取下一期开奖时间
                lot.put("id", lottery.getId());
                lot.put("name", lottery.getName());
                lot.put("categoryId", categoryMap.get(lottery.getCategoryId().toString()).getCategoryId());
                lot.put("cateName", categoryMap.get(lottery.getCategoryId().toString()).getName());
                lot.put("intro", categoryMap.get(lottery.getCategoryId().toString()).getAlias());
                lot.put("isWork", lottery.getIsWork());
                lot.put("lotteryId", lottery.getLotteryId());
                lot.put("endTime", lottery.getEndTime());
                lot.put("sort", lottery.getSort());

                if (lot.get("categoryId").equals(cate.getCategoryId())) {
                    lotlist.add(lot);
                }
            }
            lotcage.put("lotterys", lotlist);

            list.add(lotcage);
        }
        return list;
    }

    @Override
    public List<LotterySet> queryInternalAllList(String type) {

        List<LotterySet> list = new ArrayList<>();
        List<LotteryCategory> categories = lotteryCategoryService.queryAllCategory(type);
        Map<String, LotteryCategory> categoryMap = new HashMap<>();
        for (LotteryCategory category : categories) {
            categoryMap.put(category.getCategoryId().toString(), category);
        }
        LotteryDTO lotteryDTO;
        Map<Integer, List<Lottery>> lotteryMap = queryLotteryGroupByCategoryId();
        List<LotteryPlayDTO> playlist = null;
        for (LotteryCategory cate : categories) {
            LotterySet lotterySet = new LotterySet();
            lotterySet.setCateID(cate.getCategoryId() + "");
            lotterySet.setCateName(cate.getName());
            lotterySet.setIntro(cate.getAlias());
            List<LotteryDTO> lotteryDTOList = new ArrayList<>();

            // 查询所有彩种
            List<Lottery> lotteries = lotteryMap.get(cate.getCategoryId());
            for (Lottery lottery : lotteries) {
                lotteryDTO = new LotteryDTO();
                BeanUtils.copyProperties(lottery, lotteryDTO);
                // 获取下一期开奖时间

                if (lottery.getLotteryId() == 1101) {//重庆时时彩
                    CqsscLotterySg next_cqsscLotterySg = getNextCqsscLotterySg();
                    if (next_cqsscLotterySg != null && null != next_cqsscLotterySg.getIdealTime()) {
                        lotteryDTO.setOpenTime(DateUtils.getTimeMillis(next_cqsscLotterySg.getIdealTime()) / 1000L);
                    } else {
                        lotteryDTO.setOpenTime(0L);
                    }
                } else {
                    lotteryDTO.setOpenTime(NextIssueTimeUtil.getNextIssueTime(lottery.getLotteryId()));
                }

                lotteryDTO.setCateName(categoryMap.get(lottery.getCategoryId().toString()).getName());
                lotteryDTO.setIntro(categoryMap.get(lottery.getCategoryId().toString()).getAlias());
                if (lottery.getLotteryId() == 1201) {
                    // 获取六合彩的开奖时间
                    ResultInfo<Map<String, Object>> nowIssueAndTime = lhcLotterySgService.getNowIssueAndTime();
                    Object openTime = nowIssueAndTime.getData().get("openTime");
                    lotteryDTO.setOpenTime((Long) openTime);
                }
                //playlist= lotteryPlayService.playList(cate.getId());
                playlist = lotteryPlayService.playList(lottery.getLotteryId());
                for (LotteryPlayDTO lotteryPlayDTO : playlist) {

                    ResultInfo<List<LhcPlayInfoDTO>> r = lotteryPlayService.queryLhcBuy(lotteryPlayDTO.getId(), lotteryDTO.getLotteryId());
                    lotteryPlayDTO.setPlayinfos(r.getData());
                    List<LotteryPlayDTO> pclist = lotteryPlayDTO.getChildren();
                    for (LotteryPlayDTO lotteryPlayDTOc : pclist) {
                        ResultInfo<List<LhcPlayInfoDTO>> rc = lotteryPlayService.queryLhcBuy(lotteryPlayDTOc.getId(), lotteryDTO.getLotteryId());
                        lotteryPlayDTOc.setPlayinfos(rc.getData());
                    }
                }

                //list.add(lotteryDTO);
                if (lotteryDTO.getCategoryId().equals(cate.getCategoryId())) {
                    lotteryDTOList.add(lotteryDTO);
                }
            }
            lotterySet.setPlaylist(playlist);
            lotterySet.setLotterys(lotteryDTOList);
            list.add(lotterySet);
        }
        return list;
    }

    private CqsscLotterySg getNextCqsscLotterySg() {
        Date nowDate = new Date();
        CqsscLotterySgExample next_example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria next_cqsscCriteria = next_example.createCriteria();
        next_cqsscCriteria.andWanIsNull();
        next_cqsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(nowDate));
        next_example.setOrderByClause("issue ASC");
        CqsscLotterySg next_cqsscLotterySg = this.cqsscLotterySgMapper.selectOneByExample(next_example);
        return next_cqsscLotterySg;
    }

    @Override
    public SscMissNumDTO queryMissValByGroup(Integer lotteryId) {
        SscMissNumDTO result;
        switch (lotteryId) {
            // 重庆时时彩
            case 1:
                CqsscLotterySgExample cqsscExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria cqsscCriteria = cqsscExample.createCriteria();
                cqsscCriteria.andWanIsNotNull();
                cqsscExample.setOffset(0);
                cqsscExample.setLimit(100);
                cqsscExample.setOrderByClause("`ideal_time` desc");
                List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(cqsscExample);
                result = CqsscSgUtils.queryGroupMissVal(cqsscLotterySgs);
                break;

            // 新疆时时彩
            case 2:
                XjsscLotterySgExample xjsscExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria xjsscCriteria = xjsscExample.createCriteria();
                xjsscCriteria.andWanIsNotNull();
                xjsscExample.setOffset(0);
                xjsscExample.setLimit(100);
                xjsscExample.setOrderByClause("ideal_time desc");
                List<XjsscLotterySg> xjsscLotterySgs = xjsscLotterySgMapper.selectByExample(xjsscExample);
                result = XjsscSgUtils.queryGroupMissVal(xjsscLotterySgs);
                break;

            // 比特币分分彩
            default:
                TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
                txffcCriteria.andWanIsNotNull();
                txffcExample.setOffset(0);
                txffcExample.setLimit(100);
                txffcExample.setOrderByClause("ideal_time desc");
                List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
                result = TxffcSgUtils.queryGroupMissVal(txffcLotterySgs);
                break;
        }
        return result;
    }

    @Override
    public Map<String, SscMissNumDTO> querySscMissVal(Integer lotteryId, Integer start, Integer end) {
        Map<String, SscMissNumDTO> map;
        switch (lotteryId) {
            // 重庆时时彩
            case 1:
                CqsscLotterySgExample cqsscExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria cqsscCriteria = cqsscExample.createCriteria();
                cqsscCriteria.andWanIsNotNull();
                cqsscExample.setOffset(0);
                cqsscExample.setLimit(100);
                cqsscExample.setOrderByClause("`ideal_time` desc");
                List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(cqsscExample);

                map = CqsscSgUtils.queryMissVal(cqsscLotterySgs, start, end);
                break;

            // 新疆时时彩
            case 2:
                // 获取新疆时时彩组选遗漏
                XjsscLotterySgExample xjsscExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria xjsscCriteria = xjsscExample.createCriteria();
                xjsscCriteria.andWanIsNotNull();
                xjsscExample.setOffset(0);
                xjsscExample.setLimit(100);
                xjsscExample.setOrderByClause("`ideal_time` desc");
                List<XjsscLotterySg> xjsscLotterySgs = xjsscLotterySgMapper.selectByExample(xjsscExample);

                map = XjsscSgUtils.queryMissVal(xjsscLotterySgs, start, end);
                break;

            // 北京PK10
            case 6:
                BjpksLotterySgExample bjpksExample = new BjpksLotterySgExample();
                BjpksLotterySgExample.Criteria bjpksCriteria = bjpksExample.createCriteria();
                bjpksCriteria.andNumberIsNotNull();
                bjpksExample.setOffset(0);
                bjpksExample.setLimit(100);
                bjpksExample.setOrderByClause("ideal_time desc");
                List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(bjpksExample);
                map = BjpksUtils.queryMissVal(bjpksLotterySgs, start, end);
                break;

            // 幸运飞艇
            case 7:
                XyftLotterySgExample xyftLotterySgExample = new XyftLotterySgExample();
                XyftLotterySgExample.Criteria sgCriteria = xyftLotterySgExample.createCriteria();
                sgCriteria.andNumberIsNotNull();
                xyftLotterySgExample.setOffset(0);
                xyftLotterySgExample.setLimit(100);
                xyftLotterySgExample.setOrderByClause("ideal_time desc");
                List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(xyftLotterySgExample);
                map = XyftUtils.queryMissVal(xyftLotterySgs, start, end);
                break;

            // 比特币分分彩
            default:
                // 获取比特币分分彩组选遗漏
                TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
                txffcCriteria.andWanIsNotNull();
                txffcExample.setOffset(0);
                txffcExample.setLimit(100);
                txffcExample.setOrderByClause("ideal_time desc");
                List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);

                map = TxffcSgUtils.queryMissVal(txffcLotterySgs, start, end);
                break;
        }

        return map;
    }

    @Override
    public Map<String, BjpksLiangMian> queryBjpksLmMissVal() {
        BjpksLotterySgExample bjpksExample = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = bjpksExample.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        bjpksExample.setOffset(0);
        bjpksExample.setLimit(30);
        bjpksExample.setOrderByClause("ideal_time desc");
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(bjpksExample);
        return BjpksUtils.queryLiangMianMissVal(bjpksLotterySgs);
    }

    @Override
    public PageResult<List<LotterySgModel>> queryOpenException(Integer lotteryId, String date, Integer pageNo, Integer pageSize) {
        List<LotterySgModel> list = new ArrayList();
        Integer offset = (pageNo - 1) * pageSize;
        int total = 0;
        switch (lotteryId) {
            case 1:
                total = lotteryBeanMapper.countCqsscOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryCqsscOpenExceptionList(date, offset, pageSize);
                }
                break;

            case 2:
                total = lotteryBeanMapper.countXjsscOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryXjsscOpenExceptionList(date, offset, pageSize);
                }
                break;

            case 3:
                total = lotteryBeanMapper.countTxffcOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryTxffcOpenExceptionList(date, offset, pageSize);
                }
                break;

            case 5:
                date = StringUtils.isNotBlank(date) ? date + "%" : date;
                total = lotteryBeanMapper.countPcddOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryPcddOpenExceptionList(date, offset, pageSize);
                }
                break;

            case 6:
                date = StringUtils.isNotBlank(date) ? date + "%" : date;
                total = lotteryBeanMapper.countBjpksOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryBjpksOpenExceptionList(date, offset, pageSize);
                }
                break;

            case 7:
                date = StringUtils.isNotBlank(date) ? date.replace("-", "") + "%" : "";
                total = lotteryBeanMapper.countXyftOpenException(date);
                if (total > 0) {
                    list = lotteryBeanMapper.queryXyftOpenExceptionList(date, offset, pageSize);
                }
                break;
        }
        return PageResult.getPageResult(pageNo, pageSize, total, list);
    }

    @Override
    public PageResult<List<LotterySgModel>> queryOpenNumber(Integer lotteryId, String date, String status, String issue, Integer pageNo, Integer pageSize) {
        List<LotterySgModel> list = new ArrayList<>();
        Integer offset = (pageNo - 1) * pageSize;
        int total = 0;
        switch (lotteryId) {
            case 1:
                CqsscLotterySgExample cqsscExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria cqsscCriteria = cqsscExample.createCriteria();
                cqsscCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    cqsscCriteria.andDateEqualTo(date);
                }
                if (StringUtils.isNotBlank(status)) {
                    cqsscCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    cqsscCriteria.andIssueEqualTo(issue);
                }
                total = cqsscLotterySgMapper.countByExample(cqsscExample);
                if (total > 0) {
                    LotterySgModel model;
                    cqsscExample.setOffset(offset);
                    cqsscExample.setLimit(pageSize);
                    cqsscExample.setOrderByClause("`ideal_time` DESC");
                    List<CqsscLotterySg> cqsscSgList = cqsscLotterySgMapper.selectByExample(cqsscExample);
                    for (CqsscLotterySg sg : cqsscSgList) {
                        String number = sg.getWan() == null ? "" : sg.getWan() + "," + sg.getQian() + "," + sg.getBai() + "," + sg.getShi() + "," + sg.getGe();
                        model = new LotterySgModel(sg.getIssue(), number, sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;

            case 2:
                XjsscLotterySgExample xjsscExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria xjsscCriteria = xjsscExample.createCriteria();
                xjsscCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    xjsscCriteria.andDateEqualTo(date);
                }
                if (StringUtils.isNotBlank(status)) {
                    xjsscCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    xjsscCriteria.andIssueEqualTo(issue);
                }
                total = xjsscLotterySgMapper.countByExample(xjsscExample);
                if (total > 0) {
                    LotterySgModel model;
                    xjsscExample.setOffset(offset);
                    xjsscExample.setLimit(pageSize);
                    xjsscExample.setOrderByClause("`ideal_time` DESC");
                    List<XjsscLotterySg> xjsscSgList = xjsscLotterySgMapper.selectByExample(xjsscExample);
                    for (XjsscLotterySg sg : xjsscSgList) {
                        String number = sg.getWan() == null ? "" : sg.getWan() + "," + sg.getQian() + "," + sg.getBai() + "," + sg.getShi() + "," + sg.getGe();
                        model = new LotterySgModel(sg.getIssue(), number, sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;

            case 3:
                TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
                txffcCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    txffcCriteria.andDateEqualTo(date);
                }
                if (StringUtils.isNotBlank(status)) {
                    txffcCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    txffcCriteria.andIssueEqualTo(issue);
                }
                total = txffcLotterySgMapper.countByExample(txffcExample);
                if (total > 0) {
                    LotterySgModel model;
                    txffcExample.setOffset(offset);
                    txffcExample.setLimit(pageSize);
                    txffcExample.setOrderByClause("`ideal_time` DESC");
                    List<TxffcLotterySg> txffcSgList = txffcLotterySgMapper.selectByExample(txffcExample);
                    for (TxffcLotterySg sg : txffcSgList) {
                        String number = sg.getWan() == null ? "" : sg.getWan() + "," + sg.getQian() + "," + sg.getBai() + "," + sg.getShi() + "," + sg.getGe();
                        model = new LotterySgModel(sg.getIssue(), number, sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;

            case 5:
                PceggLotterySgExample pcddExample = new PceggLotterySgExample();
                PceggLotterySgExample.Criteria pcddCriteria = pcddExample.createCriteria();
                pcddCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    pcddCriteria.andIdealTimeLike(date + "%");
                }
                if (StringUtils.isNotBlank(status)) {
                    pcddCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    pcddCriteria.andIssueEqualTo(issue);
                }
                total = pceggLotterySgMapper.countByExample(pcddExample);
                if (total > 0) {
                    LotterySgModel model;
                    pcddExample.setOffset(offset);
                    pcddExample.setLimit(pageSize);
                    pcddExample.setOrderByClause("`ideal_time` DESC");
                    List<PceggLotterySg> txffcSgList = pceggLotterySgMapper.selectByExample(pcddExample);
                    for (PceggLotterySg sg : txffcSgList) {
                        model = new LotterySgModel(sg.getIssue(), sg.getNumber(), sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;

            case 6:
                BjpksLotterySgExample bjpksExample = new BjpksLotterySgExample();
                BjpksLotterySgExample.Criteria bjpksCriteria = bjpksExample.createCriteria();
                bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    bjpksCriteria.andIdealTimeLike(date + "%");
                }
                if (StringUtils.isNotBlank(status)) {
                    bjpksCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    bjpksCriteria.andIssueEqualTo(issue);
                }
                total = bjpksLotterySgMapper.countByExample(bjpksExample);
                if (total > 0) {
                    LotterySgModel model;
                    bjpksExample.setOffset(offset);
                    bjpksExample.setLimit(pageSize);
                    bjpksExample.setOrderByClause("`ideal_time` DESC");
                    List<BjpksLotterySg> txffcSgList = bjpksLotterySgMapper.selectByExample(bjpksExample);
                    for (BjpksLotterySg sg : txffcSgList) {
                        model = new LotterySgModel(sg.getIssue(), sg.getNumber(), sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;

            case 7:
                XyftLotterySgExample xyftExample = new XyftLotterySgExample();
                XyftLotterySgExample.Criteria xyftCriteria = xyftExample.createCriteria();
                xyftCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (StringUtils.isNotBlank(date)) {
                    xyftCriteria.andIssueLike(date.replace("-", "") + "%");
                }
                if (StringUtils.isNotBlank(status)) {
                    xyftCriteria.andOpenStatusEqualTo(status);
                }
                if (StringUtils.isNotBlank(issue)) {
                    xyftCriteria.andIssueEqualTo(issue);
                }
                total = xyftLotterySgMapper.countByExample(xyftExample);
                if (total > 0) {
                    LotterySgModel model;
                    xyftExample.setOffset(offset);
                    xyftExample.setLimit(pageSize);
                    xyftExample.setOrderByClause("`ideal_time` DESC");
                    List<XyftLotterySg> txffcSgList = xyftLotterySgMapper.selectByExample(xyftExample);
                    for (XyftLotterySg sg : txffcSgList) {
                        model = new LotterySgModel(sg.getIssue(), sg.getNumber(), sg.getIdealTime(), sg.getCpkNumber(), sg.getKcwNumber(), sg.getTime(), sg.getOpenStatus());
                        list.add(model);
                    }
                }
                break;
        }
        return PageResult.getPageResult(pageNo, pageSize, total, list);
    }

    /**
     * 查询彩票所有信息：包括彩种/彩票/玩法/设置/赔率等
     *
     * @param type
     * @return
     */
    public List<LotteryInfo> queryLotteryAllInfoNew(String type) {
        List<LotteryInfo> list = RedisBusinessUtil.getLotteryAllInfo(LotteryTypeEnum.LOTTERY.name());
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        }
        long start = System.currentTimeMillis();
        //result
        List<LotteryInfo> lotteryInfoList = new ArrayList<>();
        //彩票分类，cache 后续使用
        Map<Integer, LotteryAllDTO> lotteryAllDTOMap = new HashMap<>();
        //查询所有彩种
        List<LotteryCategory> categoryList = lotteryCategoryService.queryAllCategory(type);
        //查询彩票
        Map<Integer, List<Lottery>> lotteryMap = this.queryLotteryGroupByCategoryId();

        //封装结果：填充彩种，彩票信息
        assembleLotteryInfo(categoryList, lotteryMap, lotteryInfoList, lotteryAllDTOMap);

        //查询玩法，一级玩法；二级玩法
        List<Integer> categoryIdList = categoryList.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
        Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap = lotteryPlayService.selectAllLotteryPlayDTOByCategoryIds(categoryIdList);

        //查询玩法设置
        List<Integer> playIdList = getPlayIdList(lotteryPlayAllDTOMap);
        Map<String, LotteryPlaySetting> playSettingMap = lotteryPlaySettingService.queryLotteryPlaySettingMap(playIdList);

        //查询赔率
        List<Integer> playSettingIdList = playSettingMap.values().stream().map(item -> item.getId()).collect(Collectors.toList());
        Map<Integer, List<LotteryPlayOdds>> playOddsMap = lotteryPlayOddsService.selectOddsListBySettingIdList(playSettingIdList);

        //封装结果：填充玩法，玩法设置，赔率等信息
        assembleLotteryPlayInfo(lotteryInfoList, lotteryPlayAllDTOMap, playSettingMap, playOddsMap, lotteryAllDTOMap);

        //sort 彩种/彩票 按 sort 倒序排序
        lotteryInfoList.sort(Comparator.comparing(LotteryInfo::getSort).reversed());
        //彩票，玩法，子玩法倒序排序
        lotteryInfoList.forEach(item -> {
                    if (CollectionUtil.isNotEmpty(item.getLotterys())) {
                        item.getLotterys().sort(Comparator.comparing(LotteryAllDTO::getSort).reversed());
                        item.getLotterys().forEach(play -> {
                            if (null != play.getPlays() && play.getPlays().size() > 1) {
                                play.getPlays().sort(Comparator.comparing(LotteryPlayAllDTO::getSort).reversed());
                                play.getPlays().forEach(parent -> {
                                    if (null != parent.getPlayChildren() && parent.getPlayChildren().size() > 1) {
                                        parent.getPlayChildren().sort(Comparator.comparing(LotteryPlayAllDTO::getSort).reversed());
                                    }
                                });
                            }
                        });
                    }
                }
        );

        long end = System.currentTimeMillis();
        logger.info("method queryLotteryAllInfoNew used times:{} ms", end - start);
        //logger.info("lotteryInfoList info:{}", JSONObject.toJSONString(lotteryInfoList));
        //缓存信息
        RedisBusinessUtil.addLotteryAllInfo(type, lotteryInfoList);

        return lotteryInfoList;
    }

    private List<Integer> getPlayIdList(Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap) {
        List<Integer> playIdList = new ArrayList<>();
        for (Map.Entry<Integer, List<LotteryPlayAllDTO>> entry : lotteryPlayAllDTOMap.entrySet()) {
            for (LotteryPlayAllDTO dto : entry.getValue()) {
                getAllPlayId(dto, playIdList);
            }
        }
        playIdList = playIdList.stream().distinct().collect(Collectors.toList());
        logger.info("getPlayIdList size:{}.", playIdList.size());
        return playIdList;
    }

    private void getAllPlayId(LotteryPlayAllDTO dto, List<Integer> playIdList) {
        playIdList.add(dto.getId());
        List<LotteryPlayAllDTO> playChildren = dto.getPlayChildren();
        if (null != playChildren && playChildren.size() > 0) {
            for (LotteryPlayAllDTO child : playChildren) {
                getAllPlayId(child, playIdList);
            }
        }
    }

    private void assembleLotteryPlayInfo(List<LotteryInfo> lotteryInfoList,
                                         Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap,
                                         Map<String, LotteryPlaySetting> playSettingMap,
                                         Map<Integer, List<LotteryPlayOdds>> playOddsMap,
                                         Map<Integer, LotteryAllDTO> lotteryAllDTOMap) {
        SysParameter systemInfo = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
        Double defaultDivisor = Double.parseDouble(systemInfo.getParamValue());
        for (LotteryInfo lotteryInfo : lotteryInfoList) {
            for (LotteryAllDTO lotteryAllDTO : lotteryInfo.getLotterys()) {
                lotteryAllDTO.setPlays(lotteryPlayAllDTOMap.get(lotteryAllDTO.getLotteryId()));
                if (CollectionUtils.isEmpty(lotteryAllDTO.getPlays())) {
                    continue;
                }
                for (LotteryPlayAllDTO playAllDTO : lotteryAllDTO.getPlays()) {
                    if (CollectionUtils.isEmpty(playAllDTO.getPlayChildren())) {
                        assembleLotterySettingInfo(playAllDTO, playSettingMap, playOddsMap, lotteryAllDTOMap, defaultDivisor);
                        continue;
                    }
                    for (LotteryPlayAllDTO childPlayDTO : playAllDTO.getPlayChildren()) {
                        assembleLotterySettingInfo(childPlayDTO, playSettingMap, playOddsMap, lotteryAllDTOMap, defaultDivisor);
                    }
                }
            }
        }
    }

    public void assembleLotterySettingInfo(LotteryPlayAllDTO playAllDTO,
                                           Map<String, LotteryPlaySetting> playSettingMap,
                                           Map<Integer, List<LotteryPlayOdds>> playOddsMap,
                                           Map<Integer, LotteryAllDTO> lotteryAllDTOMap,
                                           Double defaultDivisor) {
        LotteryPlaySetting playSetting = playSettingMap.get(playAllDTO.getId());
        if (null == playSetting) {
            logger.info("lotteryPlay have no playSetting by lotteryPlayId:{}", playAllDTO.getId());
            return;
        }
        LotteryPlaySettingDTO playSettingDTO = new LotteryPlaySettingDTO();
        BeanUtils.copyProperties(playSettingMap.get(playAllDTO.getId()), playSettingDTO);
        playAllDTO.setSetting(playSettingDTO);

        //assembleLotteryOddsInfo
        List<LotteryPlayOdds> playOdds = playOddsMap.get(playSettingDTO.getId());
        if (null == playOdds || playOdds.size() == 0) {
            logger.info("lotterySetting have no playOdds by lotteryPlayId:{},settingId:{}", playAllDTO.getId(), playSettingDTO.getId());
            return;
        }
        List<LotteryPlayAllOdds> oddsList = new ArrayList<>();
        for (LotteryPlayOdds odds : playOdds) {
            LotteryPlayAllOdds playAllOdds = LotteryPlayAllOdds.newInstance(odds.getId(), odds.getSettingId(), odds.getName(), null);
            LotteryAllDTO lotteryAllDTO = lotteryAllDTOMap.get(playAllDTO.getLotteryId());
            Double divisor = lotteryAllDTO.getMaxOdds();
            divisor = null == divisor || divisor <= 0 ? defaultDivisor : divisor;
            // 获取总注数/中奖注数
            String winCount = odds.getWinCount();
            String totalCount = odds.getTotalCount();

            // 判断是否设置赔率
            if (!(StringUtils.isBlank(winCount) || StringUtils.isBlank(totalCount))) {
                playAllOdds.setOdds(CaipiaoUtils.getLotteryPlayOdds(totalCount, winCount, divisor));
            }
            oddsList.add(playAllOdds);
        }
        playAllDTO.setOddsList(oddsList);
    }

    private void assembleLotteryInfo(List<LotteryCategory> categoryList,
                                     Map<Integer, List<Lottery>> lotteryMap,
                                     List<LotteryInfo> lotteryInfoList,
                                     Map<Integer, LotteryAllDTO> lotteryAllDTOMap) {

        for (LotteryCategory category : categoryList) {
            LotteryInfo lotteryInfo = new LotteryInfo();
            lotteryInfo.setCateID(String.valueOf(category.getCategoryId()));
            lotteryInfo.setCateName(category.getName());
            lotteryInfo.setIntro(category.getAlias());
            lotteryInfo.setSort(category.getSort());
            lotteryInfo.setIsWork(category.getIsWork());
            List<Lottery> lotteries = lotteryMap.get(category.getCategoryId());
            if (null == lotteries || lotteries.size() == 0) {
                continue;
            }
            List<LotteryAllDTO> lotteryAllDTOList = new ArrayList<>();
            for (Lottery lottery : lotteries) {
                LotteryAllDTO lotteryAllDTO = new LotteryAllDTO();
                lotteryAllDTO.setId(lottery.getId());
                lotteryAllDTO.setName(lottery.getName());
                lotteryAllDTO.setCategoryId(lottery.getCategoryId());
                lotteryAllDTO.setLotteryId(lottery.getLotteryId());
                lotteryAllDTO.setIsWork(lottery.getIsWork());
                lotteryAllDTO.setEndTime(lottery.getEndTime());
                lotteryAllDTO.setMaxOdds(lottery.getMaxOdds());
                lotteryAllDTO.setMinOdds(lottery.getMinOdds());
                lotteryAllDTO.setSort(lottery.getSort());
                lotteryAllDTOList.add(lotteryAllDTO);
                lotteryAllDTOMap.put(lottery.getLotteryId(), lotteryAllDTO);
            }
            lotteryInfo.setLotterys(lotteryAllDTOList);
            lotteryInfoList.add(lotteryInfo);
        }
    }

    @Override
    public List<LotteryInfo> queryLotteryAllInfo(String type) {
        return queryLotteryAllInfoNew(type);
    }

    @Override
    public Lottery selectLotteryByLotteryId(Integer lotteryId) {
        if (null == lotteryId || lotteryId <= 0) {
            return null;
        }
        Lottery lottery = RedisBusinessUtil.get(RedisKeys.LOTTERY_KEY + lotteryId);
        if (null != lottery) {
            return lottery;
        }

        List<Lottery> lotteries = queryAllLotteryFromCache();
        Optional<Lottery> optional = lotteries
                .stream()
                .filter(item -> lotteryId.equals(item.getLotteryId()))
                .findFirst();
        lottery = optional.isPresent() ? optional.get() : null;
        RedisBusinessUtil.set(RedisKeys.LOTTERY_KEY + lotteryId, lottery);
        return lottery;
    }

    @Override
    public Lottery selectLotteryForName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        List<Lottery> lotteries = queryAllLotteryFromCache();
        Optional<Lottery> optional = lotteries
                .stream()
                .filter(item -> name.equals(item.getName()))
                .findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public List<Lottery> lotteryList(Integer categoryId) {
        List<Lottery> lotteries = queryAllLotteryFromCache();
        if (null != categoryId && categoryId > 0 && !CollectionUtils.isEmpty(lotteries)) {
            lotteries = lotteries
                    .stream()
                    .filter(item -> categoryId.equals(item.getCategoryId()))
                    .collect(Collectors.toList());
        }
        return CollectionUtils.isEmpty(lotteries) ? new LinkedList<Lottery>() : lotteries;
    }

    @Override
    public List<Lottery> queryAlllotteryList() {
        List<Lottery> lotteries = queryAllLotteryFromCache();
        return lotteries;
    }

    @Override
    public List<LotteryFavoriteDTO> queryLotteryByLotteryFavorites(Integer uid) {

        List<LotteryFavoriteDTO> lotteryFavoriteDTOList = (List<LotteryFavoriteDTO>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_USER_DATA_PREFIX + uid);
        if (!CollectionUtils.isEmpty(lotteryFavoriteDTOList)) {
            return lotteryFavoriteDTOList;
        }

        List<LotteryFavorite> lotteryFavorites = lotteryFavoriteReadService.queryByUserId(uid);
        lotteryFavoriteDTOList = queryLotteryFavorites(lotteryFavorites);
        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_FAVORITE_USER_DATA_PREFIX + uid, lotteryFavoriteDTOList);

        return lotteryFavoriteDTOList;
    }

    private List<LotteryFavoriteDTO> queryLotteryFavorites(List<LotteryFavorite> lotteryFavorites) {
        List<LotteryFavoriteDTO> lotteryFavoriteDTOList = new ArrayList<>();
        List<Integer> lotteryFavoriteList = lotteryFavorites.stream().map(item -> item.getLotteryId()).collect(Collectors.toList());
        List<Map<String, Object>> list = lotteryMapperExt.queryByLotteryFavorites(lotteryFavoriteList);
        for (Map<String, Object> map : list) {
            LotteryFavoriteDTO dto = new LotteryFavoriteDTO();
            dto.setId((Integer) map.get("id"));
            dto.setLotteryId((Integer) map.get("lotteryId"));
            dto.setName((String) map.get("name"));
            dto.setIcon((String) map.get("icon"));
            dto.setIntro((String) map.get("intro"));
            dto.setSort(lotteryFavoriteList.indexOf(dto.getLotteryId()));
            dto.setEndTime((Integer) map.get("endTime"));
            dto.setCategoryId((Integer) map.get("categoryId"));
            dto.setCateName((String) map.get("cateName"));
            dto.setCategorySort((Integer) map.get("categorySort"));
            dto.setIsWork((Integer) map.get("isWork"));
            lotteryFavoriteDTOList.add(dto);
        }
        lotteryFavoriteDTOList.sort(Comparator.comparing(LotteryFavoriteDTO::getSort));
        return lotteryFavoriteDTOList;
    }

    @Override
    public List<LotteryFavoriteDTO> queryDefaultLotteryFavorites() {
        List<LotteryFavoriteDTO> lotteryFavoriteDTOList = (List<LotteryFavoriteDTO>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_USER_DATA_DEFAULT);
        if (!CollectionUtils.isEmpty(lotteryFavoriteDTOList)) {
            return lotteryFavoriteDTOList;
        }

        List<LotteryFavorite> lotteryFavorites = lotteryFavoriteReadService.queryDefaultLotteryFavorite();
        if (CollectionUtils.isEmpty(lotteryFavorites)) {
            return lotteryFavoriteDTOList;
        }

        lotteryFavoriteDTOList = queryLotteryFavorites(lotteryFavorites);
        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_FAVORITE_USER_DATA_DEFAULT, lotteryFavoriteDTOList);

        return lotteryFavoriteDTOList;
    }

    public List<Lottery> queryAllLotteryFromCache(boolean fetchDeletedRecord, String listKey, String mapKey) {
        List<Lottery> list = (List<Lottery>) redisTemplate.opsForValue().get(listKey);
        if (CollectionUtils.isEmpty(list)) {
            LotteryExample example = new LotteryExample();
            if (!fetchDeletedRecord) {
                example.createCriteria().andIsDeleteEqualTo(false);
            }
            example.setOrderByClause("sort desc");
            list = lotteryMapper.selectByExample(example);
            redisTemplate.opsForValue().set(listKey, list);

            //cache as map
            Map<Integer, Lottery> lotteryMap = new HashMap<>();
            for (Lottery lottery : list) {
                lotteryMap.put(lottery.getId(), lottery);
                lotteryMap.put(lottery.getLotteryId(), lottery);
            }
            redisTemplate.opsForValue().set(mapKey, lotteryMap);
        }
        return list;
    }

    @Override
    public List<Lottery> queryAllLotteryFromCache() {
        return queryAllLotteryFromCache(false, RedisKeys.LOTTERY_LIST_KEY, RedisKeys.LOTTERY_MAP_KEY);
    }

    @Override
    public Map<Integer, List<Lottery>> queryLotteryGroupByCategoryId() {
        List<Lottery> lotteries = queryAllLotteryFromCache();
        Map<Integer, List<Lottery>> lotteryMap = new HashMap<>();
        for (Lottery lottery : lotteries) {
            List<Lottery> lotteryList;
            if (lotteryMap.containsKey(lottery.getCategoryId())) {
                lotteryList = lotteryMap.get(lottery.getCategoryId());
            } else {
                lotteryList = new ArrayList<>();
            }
            lotteryList.add(lottery);
            lotteryMap.put(lottery.getCategoryId(), lotteryList);
        }
        return lotteryMap;
    }

    @Override
    public HotFavoriteDTO getFavorite() {
        List<LotteryFavoriteDTO> lotteryFavoriteDTOList = (List<LotteryFavoriteDTO>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_FAVORITE_USER_DATA_DEFAULT);

        if (CollectionUtil.isNotEmpty(lotteryFavoriteDTOList)) {
            HotFavoriteDTO hotFavorite = this.getRedisPageInfo(lotteryFavoriteDTOList);

            if (null != hotFavorite) {
                return hotFavorite;
            }
        }
        List<LotteryFavorite> lotteryFavorites = this.getFavoriteData();

        if (CollectionUtil.isNotEmpty(lotteryFavorites)) {
            HotFavoriteDTO hotFavorite = this.getPageInfo(lotteryFavorites);

            if (null != hotFavorite) {
                return hotFavorite;
            }
        }
        return new HotFavoriteDTO();
    }


    public List<LotteryFavorite> getFavoriteData() {
        LotteryFavoriteExample example = new LotteryFavoriteExample();
        //userId=0 为用户默认收藏的彩票
        example.createCriteria().andUserIdEqualTo(0);
        List<LotteryFavorite> lotteryFavorites = lotteryFavoriteMapper.selectByExample(example);
        return lotteryFavorites;
    }

    public HotFavoriteDTO getRedisPageInfo(List<LotteryFavoriteDTO> lotteryFavoriteDTOList) {
        HotFavoriteDTO hotFavorite = new HotFavoriteDTO();

        if (lotteryFavoriteDTOList.size() != 15) {
            return null;
        }
        LotteryFavoriteDTO lotteryOne = lotteryFavoriteDTOList.get(0);
        hotFavorite.setCategoryIdOne(lotteryOne.getCategoryId());
        hotFavorite.setLotteryIdOne(lotteryOne.getLotteryId());

        LotteryFavoriteDTO lotteryTwo = lotteryFavoriteDTOList.get(1);
        hotFavorite.setCategoryIdTwo(lotteryTwo.getCategoryId());
        hotFavorite.setLotteryIdTwo(lotteryTwo.getLotteryId());

        LotteryFavoriteDTO lotteryThree = lotteryFavoriteDTOList.get(2);
        hotFavorite.setCategoryIdThree(lotteryThree.getCategoryId());
        hotFavorite.setLotteryIdThree(lotteryThree.getLotteryId());

        LotteryFavoriteDTO lotteryFour = lotteryFavoriteDTOList.get(3);
        hotFavorite.setCategoryIdFour(lotteryFour.getCategoryId());
        hotFavorite.setLotteryIdFour(lotteryFour.getLotteryId());

        LotteryFavoriteDTO lotteryFive = lotteryFavoriteDTOList.get(4);
        hotFavorite.setCategoryIdFive(lotteryFive.getCategoryId());
        hotFavorite.setLotteryIdFive(lotteryFive.getLotteryId());

        LotteryFavoriteDTO lotterySix = lotteryFavoriteDTOList.get(5);
        hotFavorite.setCategoryIdSix(lotterySix.getCategoryId());
        hotFavorite.setLotteryIdSix(lotterySix.getLotteryId());

        LotteryFavoriteDTO lotterySeven = lotteryFavoriteDTOList.get(6);
        hotFavorite.setCategoryIdSeven(lotterySeven.getCategoryId());
        hotFavorite.setLotteryIdSeven(lotterySeven.getLotteryId());

        LotteryFavoriteDTO lotteryEight = lotteryFavoriteDTOList.get(7);
        hotFavorite.setCategoryIdEight(lotteryEight.getCategoryId());
        hotFavorite.setLotteryIdEight(lotteryEight.getLotteryId());

        LotteryFavoriteDTO lotteryNine = lotteryFavoriteDTOList.get(8);
        hotFavorite.setCategoryIdNine(lotteryNine.getCategoryId());
        hotFavorite.setLotteryIdNine(lotteryNine.getLotteryId());

        LotteryFavoriteDTO lotteryTen = lotteryFavoriteDTOList.get(9);
        hotFavorite.setCategoryIdTen(lotteryTen.getCategoryId());
        hotFavorite.setLotteryIdTen(lotteryTen.getLotteryId());

        LotteryFavoriteDTO lotteryEleven = lotteryFavoriteDTOList.get(10);
        hotFavorite.setCategoryIdEleven(lotteryEleven.getCategoryId());
        hotFavorite.setLotteryIdEleven(lotteryEleven.getLotteryId());

        LotteryFavoriteDTO lotteryTwelve = lotteryFavoriteDTOList.get(11);
        hotFavorite.setCategoryIdTwelve(lotteryTwelve.getCategoryId());
        hotFavorite.setLotteryIdTwelve(lotteryTwelve.getLotteryId());

        LotteryFavoriteDTO lotteryThirtenn = lotteryFavoriteDTOList.get(12);
        hotFavorite.setCategoryIdThirtenn(lotteryThirtenn.getCategoryId());
        hotFavorite.setLotteryIdThirtenn(lotteryThirtenn.getLotteryId());

        LotteryFavoriteDTO lotteryFourteen = lotteryFavoriteDTOList.get(13);
        hotFavorite.setCategoryIdFourteen(lotteryFourteen.getCategoryId());
        hotFavorite.setLotteryIdFourteen(lotteryFourteen.getLotteryId());

        LotteryFavoriteDTO lotteryFifteen = lotteryFavoriteDTOList.get(14);
        hotFavorite.setCategoryIdFifteen(lotteryFifteen.getCategoryId());
        hotFavorite.setLotteryIdFifteen(lotteryFifteen.getLotteryId());
        return hotFavorite;
    }

    public HotFavoriteDTO getPageInfo(List<LotteryFavorite> lotteryFavorites) {
        HotFavoriteDTO hotFavorite = new HotFavoriteDTO();
        List<Integer> lotteryFavoriteList = lotteryFavorites.stream().map(item -> item.getLotteryId()).collect(Collectors.toList());
        List<Map<String, Object>> list = lotteryMapperExt.queryByLotteryFavorites(lotteryFavoriteList);

        if (list.size() != 15) {
            return null;
        }
        Map<String, Object> mapOne = list.get(0);
        hotFavorite.setCategoryIdOne((Integer) mapOne.get("categoryId"));
        hotFavorite.setLotteryIdOne((Integer) mapOne.get("lotteryId"));

        Map<String, Object> mapTwo = list.get(1);
        hotFavorite.setCategoryIdTwo((Integer) mapTwo.get("categoryId"));
        hotFavorite.setLotteryIdTwo((Integer) mapTwo.get("lotteryId"));

        Map<String, Object> mapThree = list.get(2);
        hotFavorite.setCategoryIdThree((Integer) mapThree.get("categoryId"));
        hotFavorite.setLotteryIdThree((Integer) mapThree.get("lotteryId"));

        Map<String, Object> mapFour = list.get(3);
        hotFavorite.setCategoryIdFour((Integer) mapFour.get("categoryId"));
        hotFavorite.setLotteryIdFour((Integer) mapFour.get("lotteryId"));

        Map<String, Object> mapFive = list.get(4);
        hotFavorite.setCategoryIdFive((Integer) mapFive.get("categoryId"));
        hotFavorite.setLotteryIdFive((Integer) mapFive.get("lotteryId"));

        Map<String, Object> mapSix = list.get(5);
        hotFavorite.setCategoryIdSix((Integer) mapSix.get("categoryId"));
        hotFavorite.setLotteryIdSix((Integer) mapSix.get("lotteryId"));

        Map<String, Object> mapSeven = list.get(6);
        hotFavorite.setCategoryIdSeven((Integer) mapSeven.get("categoryId"));
        hotFavorite.setLotteryIdSeven((Integer) mapSeven.get("lotteryId"));

        Map<String, Object> mapEight = list.get(7);
        hotFavorite.setCategoryIdEight((Integer) mapEight.get("categoryId"));
        hotFavorite.setLotteryIdEight((Integer) mapEight.get("lotteryId"));

        Map<String, Object> mapNine = list.get(8);
        hotFavorite.setCategoryIdNine((Integer) mapNine.get("categoryId"));
        hotFavorite.setLotteryIdNine((Integer) mapNine.get("lotteryId"));

        Map<String, Object> mapTen = list.get(9);
        hotFavorite.setCategoryIdTen((Integer) mapTen.get("categoryId"));
        hotFavorite.setLotteryIdTen((Integer) mapTen.get("lotteryId"));

        Map<String, Object> mapEleven = list.get(10);
        hotFavorite.setCategoryIdEleven((Integer) mapEleven.get("categoryId"));
        hotFavorite.setLotteryIdEleven((Integer) mapEleven.get("lotteryId"));

        Map<String, Object> mapTwelve = list.get(11);
        hotFavorite.setCategoryIdTwelve((Integer) mapTwelve.get("categoryId"));
        hotFavorite.setLotteryIdTwelve((Integer) mapTwelve.get("lotteryId"));

        Map<String, Object> mapThirtenn = list.get(12);
        hotFavorite.setCategoryIdThirtenn((Integer) mapThirtenn.get("categoryId"));
        hotFavorite.setLotteryIdThirtenn((Integer) mapThirtenn.get("lotteryId"));

        Map<String, Object> mapFourteen = list.get(13);
        hotFavorite.setCategoryIdFourteen((Integer) mapFourteen.get("categoryId"));
        hotFavorite.setLotteryIdFourteen((Integer) mapFourteen.get("lotteryId"));

        Map<String, Object> mapFifteen = list.get(14);
        hotFavorite.setCategoryIdFifteen((Integer) mapFifteen.get("categoryId"));
        hotFavorite.setLotteryIdFifteen((Integer) mapFifteen.get("lotteryId"));
        return hotFavorite;
    }

    /**
     * 查询直播间彩票
     *
     * @param ids
     * @return
     */
    @Override
    public List<OptionSelectVo> queryLiveLotteryList(String ids) {
        return lotteryBeanMapper.queryLiveLotteryList(ids);
    }
}
