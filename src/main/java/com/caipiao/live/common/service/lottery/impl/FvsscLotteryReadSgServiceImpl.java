package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.LotteryInformationType;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySg;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.FivesscLotterySgMapper;
import com.caipiao.live.common.service.lottery.FvsscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.FvsscUtils;
import com.caipiao.live.common.util.lottery.NextIssueTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FvsscLotteryReadSgServiceImpl implements FvsscLotterySgServiceReadSg {

    @Autowired
    private FivesscLotterySgMapper fivesscLotterySgMapper;

    private FivesscLotterySg getNextCqsscLotterySg(){
    	Date nowDate=new Date();
  	    FivesscLotterySgExample next_example = new FivesscLotterySgExample();
  	    FivesscLotterySgExample.Criteria next_fvsscCriteria = next_example.createCriteria();
  	    next_fvsscCriteria.andWanIsNull();
  	    next_fvsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(nowDate));
        next_example.setOrderByClause("issue ASC");
        FivesscLotterySg next_fvsscLotterySg = this.fivesscLotterySgMapper.selectOneByExample(next_example);
        return next_fvsscLotterySg;
    }    
    

    
    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
      
        // 查询最近一期信息
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOrderByClause("issue DESC");
        FivesscLotterySg fvsscLotterySg = this.fivesscLotterySgMapper.selectOneByExample(example);
        String issue="";
        if (fvsscLotterySg != null) {
            Integer wan = fvsscLotterySg.getWan();
            Integer qian = fvsscLotterySg.getQian();
            Integer bai = fvsscLotterySg.getBai();
            Integer shi = fvsscLotterySg.getShi();
            Integer ge = fvsscLotterySg.getGe();
             issue = fvsscLotterySg.getIssue();
            result.put("issue", issue);
            StringBuilder number = new StringBuilder();
            result.put("number", number.append(wan).append(qian).append(bai).append(shi).append(ge).toString());
            result.put("he", wan + qian + bai + shi + ge);
//            result.put("nextIssue", NextIssueTimeUtil.nextIssueXjssc(cqsscLotterySg.getTime(), issue));
            result.put("nextIssue", NextIssueTimeUtil.getNextIssueFivessc(issue));
            
        } else {
            result.put("issue", "");
            result.put("number", "");
            result.put("he", "");
            result.put("nextIssue", "");
        }
        

        // 查询今日已售期数
        FivesscLotterySgExample example2 = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example2.createCriteria();
//        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        criteria.andWanIsNotNull();
        String date = DateUtils.formatDate(new Date(), "HH:mm:ss").compareTo("00:05:00") >= 0 ?
                DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD) : DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateEqualTo(date);
        Integer openCount = this.fivesscLotterySgMapper.countByExample(example2);
        result.put("openCount", openCount);

        // 当日开奖总期数
        Integer sumCount = 59;
        // 计算当日剩余未开奖次数
        result.put("noOpenCount", sumCount - openCount);
        FivesscLotterySg next_fvsscLotterySg=getNextCqsscLotterySg();
        //获取下一期投注截止时间
        if(next_fvsscLotterySg!=null && null!=next_fvsscLotterySg.getIdealTime()){
        	result.put("nextTime", DateUtils.getTimeMillis(next_fvsscLotterySg.getIdealTime())/ 1000L);
        }else{
        	result.put("nextTime","");
        }
        
//        result.put("nextTime", NextIssueTimeUtil.nextIssueTimeCqssc() / 1000L);
//        result.put("nextIssue", NextIssueTimeUtil.nextIssueCqssc());
        result.put("nextIssue",  NextIssueTimeUtil.getNextIssueFivessc(issue));

        return result;
    }   

    
	
    @Override
    public List<FivesscLotterySg> getSgByDatePageSize(String date,Integer pageNo, Integer pageSize) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if(!org.springframework.util.StringUtils.isEmpty(date)) {
        	if (StringUtils.isBlank(date)) {
                date = TimeHelper.date("yyyy-MM-dd");
            }
            date = date.replaceAll("-", "") + "%";
            criteria.andIssueLike(date);
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("issue DESC");
        List<FivesscLotterySg> fvsscLotterySgs = fivesscLotterySgMapper.selectByExample(example);
        return fvsscLotterySgs;
    }
	
    @Override
    public List<FivesscLotterySg> getSgByIssues(Integer issues) {
        if (issues == null) {
            issues = 30;
        }
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria fvsscCriteria = example.createCriteria();
        fvsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(issues);
        example.setOrderByClause("issue DESC");
        List<FivesscLotterySg> fvsscLotterySgs = fivesscLotterySgMapper.selectByExample(example);
        return fvsscLotterySgs;
    }  	
	
	
    @Override
	public ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize) {
    	 // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        List<FivesscLotterySg> fvsscLotterySg = getSgByDatePageSize(date, pageNo, pageSize);
        List<Map<String, Object>> list = FvsscUtils.lishiKaiJiang(fvsscLotterySg);
        return ResultInfo.ok(list);
	}
    
    
    @Override
    public ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues) {
        // 判断类型
        if (!LotteryInformationType.CQSSC_LSKJ_KJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        // 获取往期开奖结果
        List<FivesscLotterySg> fvsscLotterySgs = getSgByIssues(issues);
        // 封装数据
        List<Map<String, Object>> list = FvsscUtils.lishiKaiJiang(fvsscLotterySgs);
        return ResultInfo.ok(list);
    }    
    
    @Override
    public List<FivesscLotterySg> getSgByDate(String date) {
    	
        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        date = date.replaceAll("-", "") + "%";
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        example.setOrderByClause("issue DESC");
        List<FivesscLotterySg> fvsscLotterySgs = fivesscLotterySgMapper.selectByExample(example);
        return fvsscLotterySgs;
    }   

    /**
     * 5分时时彩今日统计
     *
     * @param type 类型（校验）
     * @param date 日期, 如2018-08-21
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> todayCount(String type, String date) {
        // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        List<FivesscLotterySg> fvsscLotterySgs = getSgByDate(date);
        if (fvsscLotterySgs == null || fvsscLotterySgs.size() == 0) {
            return ResultInfo.getInstance(null, StatusCode.NO_DATA);
        }
        Map<String, Object> map = FvsscUtils.todayCount(fvsscLotterySgs);
        return ResultInfo.ok(map);
    }
	
}
