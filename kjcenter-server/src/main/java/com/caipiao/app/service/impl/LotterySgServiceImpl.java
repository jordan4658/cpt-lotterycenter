package com.caipiao.app.service.impl;

import com.caipiao.app.service.*;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.mapper.*;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lzy
 * @create 2018-08-27 14:18
 **/
@Service
public class LotterySgServiceImpl implements LotterySgService {

    private final static Logger logger = LoggerFactory.getLogger(LotterySgServiceImpl.class);
    @Autowired
    private JssscLotterySgService jssscLotterySgService;
    @Autowired
    private TxffcLotterySgService txffcLotterySgService;
    @Autowired
    private AmlhcLotterySgService amlhcLotterySgService;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private FivesscLotterySgMapper fivesscLotterySgMapper;
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;
    @Autowired
    private FivelhcLotterySgMapper fivelhcLotterySgMapper;
    @Autowired
    private OnelhcLotterySgMapper onelhcLotterySgMapper;
    @Autowired
    private FtjspksLotterySgService ftjspksLotterySgService;
    @Autowired
    private FtjssscLotterySgService ftjssscLotterySgService;
    @Autowired
    private JspksLotterySgService jspksLotterySgService;
    @Autowired
    private OnelhcLotterySgService onelhcLotterySgService;
    @Autowired
    private FivesscLotterySgService fivesscLotterySgService;
    @Autowired
    private TensscLotterySgService tensscLotterySgService;
    @Autowired
    private FvpksLotterySgService fvpksLotterySgService;
    @Autowired
    private TenpksLotterySgService tenpksLotterySgService;
    @Autowired
    private FivelhcLotterySgService fivelhcLotterySgService;
    @Autowired
    private XyftscLotterySgService xyftscLotterySgService;


    /*
     * @Title: getNewestSgInfobyids
     * @Description: 通过ID获取多个彩种的赛果
     * @see com.caipiao.business.read.service.result.LotterySgService#
     * getNewestSgInfobyids(java.lang.String)
     */
    @Override
    public ResultInfo<Map<String, Object>> getNewSgInfoById(String id) {
        Map<String, Object> result = new HashMap<>();
//            if (null == id || "".equals(id.trim())) {
//                return getNewestSgInfo();
//            }
        id = id.trim();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
//            if (null == caipiaoTypeEnum) {
//                return getNewestSgInfo();
//            }
        switch (caipiaoTypeEnum) {
            case JSSSC:
                Map<String, Object> jsssc = jssscLotterySgService.getJssscNewestSgInfo();
                result.put(CaipiaoTypeEnum.JSSSC.getTagType(), jsssc);
                break;
            case TENSSC:
                Map<String, Object> tenssc = tensscLotterySgService.getTensscNewestSgInfo();
                result.put(CaipiaoTypeEnum.TENSSC.getTagType(), tenssc);
                break;
            case FIVESSC:
                Map<String, Object> fivessc = fivesscLotterySgService.getFivesscNewestSgInfo();
                result.put(CaipiaoTypeEnum.FIVESSC.getTagType(), fivessc);
                break;
            case ONELHC:
                Map<String, Object> onelhc = onelhcLotterySgService.getOnelhcNewestSgInfo().getData();
                result.put(CaipiaoTypeEnum.ONELHC.getTagType(), onelhc);
                break;
            case FIVELHC:
                Map<String, Object> fivelhc = fivelhcLotterySgService.getFivelhcNewestSgInfo().getData();
                result.put(CaipiaoTypeEnum.FIVELHC.getTagType(), fivelhc);
                break;
            case AMLHC:
                Map<String, Object> amlhc = amlhcLotterySgService.getSslhcNewestSgInfo().getData();
                result.put(CaipiaoTypeEnum.AMLHC.getTagType(), amlhc);
                break;
            case JSPKS:
                ResultInfo<Map<String, Object>> jspks = jspksLotterySgService.getJspksNewestSgInfo();
                result.put(CaipiaoTypeEnum.JSPKS.getTagType(), jspks.getData());
                break;
            case FIVEPKS:
                ResultInfo<Map<String, Object>> fivepks = fvpksLotterySgService.getNewestSgInfo();
                result.put(CaipiaoTypeEnum.FIVEPKS.getTagType(), fivepks.getData());
                break;
            case TENPKS:
                ResultInfo<Map<String, Object>> tenpks = tenpksLotterySgService.getNewestSgInfo();
                result.put(CaipiaoTypeEnum.TENPKS.getTagType(), tenpks.getData());
                break;
            case TXFFC:
                Map<String, Object> txffc = this.txffcLotterySgService.getNewestSgInfo();
                result.put(CaipiaoTypeEnum.TXFFC.getTagType(), txffc);
                break;
            case XYFEITSC:
                ResultInfo<Map<String, Object>> xyftsc = this.xyftscLotterySgService.getNewestSgInfo();
                result.put(CaipiaoTypeEnum.XYFEITSC.getTagType(), xyftsc.getData());
                break;
            default:
                break;
        }

        return ResultInfo.ok(result);
    }

    /*
     * @Title: getStatisticSgInfoById
     * @Description: 通过ID统计各个彩种的赛果
     * @see com.caipiao.business.read.service.result.LotterySgService#
     * getStatisticSgInfoById(java.lang.String)
     */
    @Override
    public ResultInfo<Map<String, Object>> getStatisticSgInfoById(String id, String date) {
        Map<String, Object> result = new HashMap<>();
        id = id.trim();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
        switch (caipiaoTypeEnum) {
            case JSSSC:
                Map<String, Object> jsssc = jssscLotterySgService.getJssscStasticSgInfo(date);
                result.put(CaipiaoTypeEnum.JSSSC.getTagType(), jsssc);
                break;
            case TENSSC:
                Map<String, Object> tenssc = tensscLotterySgService.getTensscStasticSgInfo(date);
                result.put(CaipiaoTypeEnum.TENSSC.getTagType(), tenssc);
                break;
            case FIVESSC:
                Map<String, Object> fivessc = fivesscLotterySgService.getFivesscStasticSgInfo(date);
                result.put(CaipiaoTypeEnum.FIVESSC.getTagType(), fivessc);
                break;
            case TXFFC:
                Map<String, Object> txffc = this.txffcLotterySgService.getTxffcStasticSgInfo(date);
                result.put(CaipiaoTypeEnum.TXFFC.getTagType(), txffc);
                break;
            default:
                break;
        }

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> getLsSgInfoByIdAndType(String id, String type, int num, String starteDate, String endDate, Integer pageNum, Integer pageSize) {
        ResultInfo<Map<String, Object>> result = null;

        id = id.trim();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
        switch (caipiaoTypeEnum) {
            case AMLHC:
                result = amlhcLotterySgService.lishiSg(type, num, starteDate, endDate, pageNum, pageSize);
                break;
            default:
                break;
        }

        return result;
    }

    @Override
    public ResultInfo<Map<String, Object>> getLsSgInfoByDate(String issue, String date, Integer pageNum, Integer pageSize) {
        ResultInfo<Map<String, Object>> result = xyftscLotterySgService.lishiSg(issue, date, pageNum, pageSize);
        return result;
    }


    @Override
    public ResultInfo<Map<String, Object>> getLsSgInfoByIdAndDay(String id, String date, Integer pageNum, Integer pageSize) {
        ResultInfo<Map<String, Object>> result = null;
//            if (null == id || "".equals(id.trim())) {
//                return getNewestSgInfo();
//            }
        id = id.trim();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagType(id);
//            if (null == caipiaoTypeEnum) {
//                return getNewestSgInfo();
//            }
        switch (caipiaoTypeEnum) {
            case TXFFC:
                result = txffcLotterySgService.lishiSg(date, pageNum, pageSize);
                break;
            case JSSSC:
                result = this.lishijssscSg(date, pageNum, pageSize);
                break;
            case TENSSC:
                result = this.lishitensscSg(date, pageNum, pageSize);
                break;
            case FIVESSC:
                result = this.lishifivesscSg(date, pageNum, pageSize);
                break;
            case ONELHC:
                result = this.lishionelhcSg(date, pageNum, pageSize);
                break;
            case FIVELHC:
                result = this.lishifivelhcSg(date, pageNum, pageSize);
                break;
            case AMLHC:
                result = this.lishisslhcSg(date, pageNum, pageSize);
                break;
            case JSPKS:
                result = this.lishijspksSg(date, pageNum, pageSize);
                break;
            case FIVEPKS:
                result = this.lishifivepksSg(date, pageNum, pageSize);
                break;
            case TENPKS:
                result = this.lishitenpksSg(date, pageNum, pageSize);
                break;
            default:
                break;
        }

        return result;
    }

    @Override
    public ResultInfo<Map<String, Object>> getLsSgInfoByIdLately(String code, Integer pageNum, Integer pageSize) {
        ResultInfo<Map<String, Object>> result = null;
//            if (null == id || "".equals(id.trim())) {
//                return getNewestSgInfo();
//            }
        code = code.trim();
        CaipiaoTypeEnum caipiaoTypeEnum = CaipiaoTypeEnum.valueOfTagEnName(code);
//            if (null == caipiaoTypeEnum) {
//                return getNewestSgInfo();
//            }
        switch (caipiaoTypeEnum) {
            case BTBFFC:
                result = txffcLotterySgService.lishiSgLately(pageNum, pageSize);
                break;
            case JSSSC:
                result = this.lishijssscSgLately(pageNum, pageSize);
                break;
            case TENSSC:
                result = this.lishitensscSgLately(pageNum, pageSize);
                break;
            case FIVESSC:
                result = this.lishifivesscSgLately(pageNum, pageSize);
                break;
            case ONELHC:
                result = this.lishionelhcSgLately(pageNum, pageSize);
                break;
            case FIVELHC:
                result = this.lishifivelhcSgLately(pageNum, pageSize);
                break;
            case AMLHC:
                result = this.lishisslhcSgLately(pageNum, pageSize);
                break;
            case JSPKS:
                result = this.lishijspksSgLately(pageNum, pageSize);
                break;
            case FIVEPKS:
                result = this.lishifivepksSgLately(pageNum, pageSize);
                break;
            case TENPKS:
                result = this.lishitenpksSgLately(pageNum, pageSize);
                break;
            default:
                break;
        }

        return result;
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitenpksSg(String date, Integer pageNum, Integer pageSize) {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        bjpksCriteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = tenbjpksLotterySgMapper.countByExample(example);
        List<TenbjpksLotterySg> bjpksLotterySgs = tenbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TenbjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
//            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            //计算冠亚军和，1-5龙虎
            if (StringUtils.isNotBlank(sg.getNumber())) {
                String number[] = sg.getNumber().split(",");
                int num1 = Integer.valueOf(number[0]);
                int num2 = Integer.valueOf(number[1]);
                int num3 = Integer.valueOf(number[2]);
                int num4 = Integer.valueOf(number[3]);
                int num5 = Integer.valueOf(number[4]);
                int num6 = Integer.valueOf(number[5]);
                int num7 = Integer.valueOf(number[6]);
                int num8 = Integer.valueOf(number[7]);
                int num9 = Integer.valueOf(number[8]);
                int num10 = Integer.valueOf(number[9]);
                String num1Lh = "龙";
                String num2Lh = "龙";
                String num3Lh = "龙";
                String num4Lh = "龙";
                String num5Lh = "龙";
                String num6Lh = "龙";
                String num7Lh = "龙";
                String num8Lh = "龙";
                String num9Lh = "龙";
                String num10Lh = "龙";
                Integer guanyaSum = Integer.valueOf(number[0]) + Integer.valueOf(number[1]);
                String BigOrSmall = "小";
                String danOrShuang = "单";
                if (guanyaSum > 11) {
                    BigOrSmall = "大";
                }
                if (guanyaSum % 2 == 0) {
                    danOrShuang = "双";
                }
                if (num1 > num10) {
                    num1Lh = "虎";
                }
                if (num2 > num9) {
                    num2Lh = "虎";
                }
                if (num3 > num8) {
                    num3Lh = "虎";
                }
                if (num4 > num7) {
                    num4Lh = "虎";
                }
                if (num5 > num6) {
                    num5Lh = "虎";
                }
                map.put("calMessage", guanyaSum + "," + BigOrSmall + "," + danOrShuang + "," + num1Lh + "," + num2Lh + "," + num3Lh + "," + num4Lh + "," + num5Lh);
            }

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitenpksSgLately(Integer pageNum, Integer pageSize) {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<TenbjpksLotterySg> bjpksLotterySgs = tenbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TenbjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", sg.getNumber());

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.TENPKS.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivepksSg(String date, Integer pageNum, Integer pageSize) {
        FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        bjpksCriteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = fivebjpksLotterySgMapper.countByExample(example);
        List<FivebjpksLotterySg> bjpksLotterySgs = fivebjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivebjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
//            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            //计算冠亚军和，1-5龙虎
            if (StringUtils.isNotBlank(sg.getNumber())) {
                String number[] = sg.getNumber().split(",");
                int num1 = Integer.valueOf(number[0]);
                int num2 = Integer.valueOf(number[1]);
                int num3 = Integer.valueOf(number[2]);
                int num4 = Integer.valueOf(number[3]);
                int num5 = Integer.valueOf(number[4]);
                int num6 = Integer.valueOf(number[5]);
                int num7 = Integer.valueOf(number[6]);
                int num8 = Integer.valueOf(number[7]);
                int num9 = Integer.valueOf(number[8]);
                int num10 = Integer.valueOf(number[9]);
                String num1Lh = "龙";
                String num2Lh = "龙";
                String num3Lh = "龙";
                String num4Lh = "龙";
                String num5Lh = "龙";
                String num6Lh = "龙";
                String num7Lh = "龙";
                String num8Lh = "龙";
                String num9Lh = "龙";
                String num10Lh = "龙";
                Integer guanyaSum = Integer.valueOf(number[0]) + Integer.valueOf(number[1]);
                String BigOrSmall = "小";
                String danOrShuang = "单";
                if (guanyaSum > 11) {
                    BigOrSmall = "大";
                }
                if (guanyaSum % 2 == 0) {
                    danOrShuang = "双";
                }
                if (num1 > num10) {
                    num1Lh = "虎";
                }
                if (num2 > num9) {
                    num2Lh = "虎";
                }
                if (num3 > num8) {
                    num3Lh = "虎";
                }
                if (num4 > num7) {
                    num4Lh = "虎";
                }
                if (num5 > num6) {
                    num5Lh = "虎";
                }
                map.put("calMessage", guanyaSum + "," + BigOrSmall + "," + danOrShuang + "," + num1Lh + "," + num2Lh + "," + num3Lh + "," + num4Lh + "," + num5Lh);
            }

            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivepksSgLately(Integer pageNum, Integer pageSize) {
        FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<FivebjpksLotterySg> bjpksLotterySgs = fivebjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivebjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", sg.getNumber());
            maps.add(map);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.FIVEPKS.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishisslhcSg(String date, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        AmlhcLotterySgExample example = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        int count = amlhcLotterySgMapper.countByExample(example);
        List<AmlhcLotterySg> lhcLotterySgs = this.amlhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishisslhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishisslhcSgLately(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        AmlhcLotterySgExample example = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        List<AmlhcLotterySg> lhcLotterySgs = this.amlhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.thirdLishisslhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.AMLHC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivelhcSg(String date, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        FivelhcLotterySgExample example = new FivelhcLotterySgExample();
        FivelhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        int count = fivelhcLotterySgMapper.countByExample(example);
        List<FivelhcLotterySg> lhcLotterySgs = this.fivelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishifivelhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivelhcSgLately(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        FivelhcLotterySgExample example = new FivelhcLotterySgExample();
        FivelhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("time DESC");
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        List<FivelhcLotterySg> lhcLotterySgs = this.fivelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.thirdLishifivelhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.FIVELHC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivesscSg(String date, Integer pageNum, Integer pageSize) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = fivesscLotterySgMapper.countByExample(example);
        List<FivesscLotterySg> lotterySgs = fivesscLotterySgMapper.selectByExample(example);
        //logger.info("lishifivesscSg lotterySgs:{}.", JSONObject.toJSONString(lotterySgs));

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivesscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getTime());
//            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai()).append(",")
                    .append(sg.getShi()).append(",").append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalSize", count);
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishifivesscSgLately(Integer pageNum, Integer pageSize) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<FivesscLotterySg> lotterySgs = fivesscLotterySgMapper.selectByExample(example);

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (FivesscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai()).append(",")
                    .append(sg.getShi()).append(",").append(sg.getGe()).toString());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.FIVESSC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitensscSg(String date, Integer pageNum, Integer pageSize) {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = tensscLotterySgMapper.countByExample(example);
        List<TensscLotterySg> lotterySgs = tensscLotterySgMapper.selectByExample(example);
        //logger.info("lishitensscSg lotterySgs:{}", JSONObject.toJSONString(lotterySgs));

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TensscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getTime());
//            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai()).append(",")
                    .append(sg.getShi()).append(",").append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishitensscSgLately(Integer pageNum, Integer pageSize) {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<TensscLotterySg> lotterySgs = tensscLotterySgMapper.selectByExample(example);
        //logger.info("lishitensscSg lotterySgs:{}", JSONObject.toJSONString(lotterySgs));

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (TensscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai()).append(",")
                    .append(sg.getShi()).append(",").append(sg.getGe()).toString());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.TENSSC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijssscSg(String date, Integer pageNum, Integer pageSize) {
        JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = jssscLotterySgMapper.countByExample(example);
        List<JssscLotterySg> lotterySgs = jssscLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JssscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getTime());
//            map.put("number", new StringBuffer().append(sg.getWan()).append(sg.getQian()).append(sg.getBai())
//                    .append(sg.getShi()).append(sg.getGe()).toString());
            map.put("numberstr", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai())
                    .append(",").append(sg.getShi()).append(",").append(sg.getGe()).toString());
            // map.put("numberstr", CaipiaoNumberFormatUtils.NumberFormat(sg.getWan(),
            // sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe()));
            map.put("he", sg.getWan() + sg.getQian() + sg.getBai() + sg.getShi() + sg.getGe());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijssscSgLately(Integer pageNum, Integer pageSize) {
        JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<JssscLotterySg> lotterySgs = jssscLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JssscLotterySg sg : lotterySgs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("issue", sg.getIssue());
            map.put("money", sg.getMoney());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", new StringBuffer().append(sg.getWan()).append(",").append(sg.getQian()).append(",").append(sg.getBai())
                    .append(",").append(sg.getShi()).append(",").append(sg.getGe()).toString());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.JSSSC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishionelhcSg(String date, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        OnelhcLotterySgExample example = new OnelhcLotterySgExample();
        OnelhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("time DESC");
        criteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        int count = onelhcLotterySgMapper.countByExample(example);
        List<OnelhcLotterySg> lhcLotterySgs = this.onelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.lishionelhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishionelhcSgLately(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        OnelhcLotterySgExample example = new OnelhcLotterySgExample();
        OnelhcLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("time DESC");
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        List<OnelhcLotterySg> lhcLotterySgs = this.onelhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = LhcUtils.thirdLishionelhcSg(lhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.ONELHC.getTagEnName());
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijspksSg(String date, Integer pageNum, Integer pageSize) {
        JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        bjpksCriteria.andIdealTimeGreaterThanOrEqualTo(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date, DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
        int count = jsbjpksLotterySgMapper.countByExample(example);
        List<JsbjpksLotterySg> bjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JsbjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
//            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            //计算冠亚军和，1-5龙虎
            if (StringUtils.isNotBlank(sg.getNumber())) {
                String number[] = sg.getNumber().split(",");
                int num1 = Integer.valueOf(number[0]);
                int num2 = Integer.valueOf(number[1]);
                int num3 = Integer.valueOf(number[2]);
                int num4 = Integer.valueOf(number[3]);
                int num5 = Integer.valueOf(number[4]);
                int num6 = Integer.valueOf(number[5]);
                int num7 = Integer.valueOf(number[6]);
                int num8 = Integer.valueOf(number[7]);
                int num9 = Integer.valueOf(number[8]);
                int num10 = Integer.valueOf(number[9]);
                String num1Lh = "龙";
                String num2Lh = "龙";
                String num3Lh = "龙";
                String num4Lh = "龙";
                String num5Lh = "龙";
                String num6Lh = "龙";
                String num7Lh = "龙";
                String num8Lh = "龙";
                String num9Lh = "龙";
                String num10Lh = "龙";
                Integer guanyaSum = Integer.valueOf(number[0]) + Integer.valueOf(number[1]);
                String BigOrSmall = "小";
                String danOrShuang = "单";
                if (guanyaSum > 11) {
                    BigOrSmall = "大";
                }
                if (guanyaSum % 2 == 0) {
                    danOrShuang = "双";
                }
                if (num1 > num10) {
                    num1Lh = "虎";
                }
                if (num2 > num9) {
                    num2Lh = "虎";
                }
                if (num3 > num8) {
                    num3Lh = "虎";
                }
                if (num4 > num7) {
                    num4Lh = "虎";
                }
                if (num5 > num6) {
                    num5Lh = "虎";
                }
                map.put("calMessage", guanyaSum + "," + BigOrSmall + "," + danOrShuang + "," + num1Lh + "," + num2Lh + "," + num3Lh + "," + num4Lh + "," + num5Lh);
            }
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishijspksSgLately(Integer pageNum, Integer pageSize) {
        JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<JsbjpksLotterySg> bjpksLotterySgs = jsbjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (JsbjpksLotterySg sg : bjpksLotterySgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getIdealTime());
            map.put("timestamp", DateUtils.parseDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
            map.put("number", sg.getNumber());
            maps.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("rows", maps.size());
        result.put("code", CaipiaoTypeEnum.JSPKS.getTagEnName());
        return ResultInfo.ok(result);
    }

}
