package com.caipiao.task.server.util;

import com.caipiao.core.library.tool.DateUtils;
import com.mapper.BjpksLotterySgMapper;
import com.mapper.CqsscLotterySgMapper;
import com.mapper.FtxyftLotterySgMapper;
import com.mapper.PceggLotterySgMapper;
import com.mapper.TjsscLotterySgMapper;
import com.mapper.XjsscLotterySgMapper;
import com.mapper.XyftLotterySgMapper;
import com.mapper.domain.BjpksLotterySg;
import com.mapper.domain.BjpksLotterySgExample;
import com.mapper.domain.CqsscLotterySg;
import com.mapper.domain.CqsscLotterySgExample;
import com.mapper.domain.FtxyftLotterySg;
import com.mapper.domain.FtxyftLotterySgExample;
import com.mapper.domain.PceggLotterySg;
import com.mapper.domain.PceggLotterySgExample;
import com.mapper.domain.TjsscLotterySg;
import com.mapper.domain.TjsscLotterySgExample;
import com.mapper.domain.XjsscLotterySg;
import com.mapper.domain.XjsscLotterySgExample;
import com.mapper.domain.XyftLotterySg;
import com.mapper.domain.XyftLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 阿里云文件上传工具类
 */
@Component
public class CommonService {

    private static final Logger logger= LoggerFactory.getLogger(CommonService.class);
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public CqsscLotterySg cqsscQueryNextSg() {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return cqsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public CqsscLotterySg cqsscQueryNextSgByIssue(String issue) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return cqsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public CqsscLotterySg cqsscQueryLastSgByIssue(String issue, boolean isLast) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andWanIsNotNull();
        }
        example.setOrderByClause("`ideal_time` desc");
        return cqsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public XjsscLotterySg xjsscQueryNextSg() {
        XjsscLotterySgExample example = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return xjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public XjsscLotterySg xjsscQueryNextSgByIssue(String issue) {
        XjsscLotterySgExample example = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return xjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public XjsscLotterySg xjsscQueryLastSgByIssue(String issue, boolean isLast) {
        XjsscLotterySgExample example = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andWanIsNotNull();
        }
        example.setOrderByClause("`ideal_time` desc");
        return xjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public TjsscLotterySg tjsscQueryNextSg() {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return tjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public TjsscLotterySg tjsscQueryNextSgByIssue(String issue) {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return tjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public TjsscLotterySg tjsscQueryLastSgByIssue(String issue, boolean isLast) {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andWanIsNotNull();
        }
        example.setOrderByClause("`ideal_time` desc");
        return tjsscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public BjpksLotterySg bjpksQueryNextSg() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return bjpksLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public BjpksLotterySg bjpksQueryNextSgByIssue(String issue) {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return bjpksLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public BjpksLotterySg bjpksQueryLastSgByIssue(String issue, boolean isLast) {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andNumberIsNotNull();
            criteria.andNumberNotEqualTo("");
        }
        example.setOrderByClause("`ideal_time` desc");
        return bjpksLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public XyftLotterySg xyftQueryNextSg() {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return xyftLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public XyftLotterySg xyftQueryNextSgByIssue(String issue) {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return xyftLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public XyftLotterySg xyftQueryLastSgByIssue(String issue, boolean isLast) {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andNumberNotEqualTo("");
            criteria.andNumberIsNotNull();
        }
        example.setOrderByClause("`ideal_time` desc");
        return xyftLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * *isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public FtxyftLotterySg ftxyftQueryLastSgByIssue(String issue, boolean isLast) {
        FtxyftLotterySgExample example = new FtxyftLotterySgExample();
        FtxyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andNumberNotEqualTo("");
            criteria.andNumberIsNotNull();
        }
        example.setOrderByClause("`ideal_time` desc");
        return ftxyftLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public FtxyftLotterySg ftxyftQueryNextSgByIssue(String issue) {
        FtxyftLotterySgExample example = new FtxyftLotterySgExample();
        FtxyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return ftxyftLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号及时间
     *
     * @return
     */
    public PceggLotterySg pceggQueryNextSg() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("ideal_time ASC");
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public PceggLotterySg pceggQueryNextSgByIssue(String issue) {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(issue);
        example.setOrderByClause("`ideal_time` ASC");
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取上一期期号信息
     * isLast:  true 上一期信息    false:开奖结果不为空的上一期信息
     *
     * @return
     */
    public PceggLotterySg pceggQueryLastSgByIssue(String issue, boolean isLast) {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLessThan(issue);
        if (!isLast) {
            criteria.andNumberIsNotNull();
            criteria.andNumberNotEqualTo("");
        }
        example.setOrderByClause("`ideal_time` desc");
        return pceggLotterySgMapper.selectOneByExample(example);
    }

}
