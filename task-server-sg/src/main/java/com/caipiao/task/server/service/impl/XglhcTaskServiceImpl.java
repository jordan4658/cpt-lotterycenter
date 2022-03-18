package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.XglhcTaskService;
import com.mapper.LhcHandicapMapper;
import com.mapper.domain.LhcHandicap;
import com.mapper.domain.LhcHandicapExample;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class XglhcTaskServiceImpl implements XglhcTaskService {

    private static final Logger logger = LoggerFactory.getLogger(XglhcTaskServiceImpl.class);
    @Autowired
    private LhcHandicapMapper lhcHandicapMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 录入预期赛果信息
     */
    @Override
    public void addXglhcPrevSg() {
        try {
            /*参数配置页面*/
            String serieInfo = "https://bet.hkjc.com/marksix/Fixtures.aspx?lang=CH";

            /*用來封裝要保存的参数*/
            Map<String, Object> map = new HashMap<>();

            /*取得车系参数配置页面文档*/
            Document document = Jsoup.connect(serieInfo).timeout(10000).post();

            /*取得script下面的JS变量*/
            Elements e = document.getElementsByTag("script");

            /*循环遍历script下面的JS变量*/
            for (Element element : e) {

                /*取得JS变量数组*/
                String[] data = element.data().toString().split("var");

                /*取得单个JS变量*/
                for (String variable : data) {
                    if (variable.contains("dataJson")) {
                        variable = variable.replace("dataJson = ", "");
                        variable = variable.substring(0, variable.length() - 7);
                        JSONObject object = JSONObject.parseObject(variable);
                        JSONArray dateArray = object.getJSONObject("drawDates").getJSONArray("drawYear");

                        for (int i = 0; i < dateArray.size(); i++) {
                            JSONObject dateObject = dateArray.getJSONObject(i);
                            int year = dateObject.getInteger("@year");

                            JSONArray monthArray = dateObject.getJSONArray("drawMonth");
                            for (int j = 0; j < monthArray.size(); j++) {
                                JSONObject monthJsonObject = monthArray.getJSONObject(j);
                                int month = monthJsonObject.getInteger("@month") - 1;

                                JSONArray dayArray = monthJsonObject.getJSONArray("drawDate");
                                for (int k = 0; k < dayArray.size(); k++) {
                                    int day = dayArray.getJSONObject(k).getInteger("@date");
                                    int draw = dayArray.getJSONObject(k).getInteger("@draw");
                                    int preSell = dayArray.getJSONObject(k).getInteger("@preSell");

                                    if (draw == 0 && preSell == 1) {
                                        continue;
                                    }
//									[{"@date":"2","@draw":"0","@preSell":"1","@jackpot":"0"}
//									"@draw":"0","@preSell":"1"
                                    //当 draw = 0，preSell = 1 过滤掉

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.YEAR, year);
                                    calendar.set(Calendar.MONTH, month);
                                    calendar.set(Calendar.DAY_OF_MONTH, day);
                                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.set(Calendar.SECOND, 0);
                                    Date startDate = calendar.getTime();
                                    if (startDate.getTime() < DateUtils.parseDate("2019-01-01 00:00:00", DateUtils.fullDatePattern).getTime()) {
                                        continue;
                                    }
                                    System.out.println(DateUtils.formatDate(startDate, DateUtils.fullDatePattern));
                                    Calendar nextCalendar = calendar;
                                    nextCalendar.add(Calendar.DAY_OF_MONTH, 1);
                                    Date nextDate = nextCalendar.getTime();

                                    LhcHandicapExample lhcHandicapExample = new LhcHandicapExample();
                                    LhcHandicapExample.Criteria criteria = lhcHandicapExample.createCriteria();
                                    criteria.andStartlottoTimeBetween(DateUtils.formatDate(startDate, DateUtils.fullDatePattern), DateUtils.formatDate(nextDate, DateUtils.fullDatePattern));
                                    LhcHandicap lhcHandicap = lhcHandicapMapper.selectOneByExample(lhcHandicapExample);
                                    System.out.println(DateUtils.formatDate(startDate, DateUtils.fullDatePattern) + "," + DateUtils.formatDate(nextDate, DateUtils.fullDatePattern));
                                    if (lhcHandicap == null) {
                                        LhcHandicap lhcHandicap1 = new LhcHandicap();
                                        String kaijiangTime = " 21:30:00";
                                        String fengpanTime = " 21:20:00";
                                        if (redisTemplate.opsForValue().get("kaijiangTime") != null) {
                                            kaijiangTime = " " + String.valueOf(redisTemplate.opsForValue().get("kaijiangTime"));
                                        }
                                        if (redisTemplate.opsForValue().get("fengpanTime") != null) {
                                            fengpanTime = " " + String.valueOf(redisTemplate.opsForValue().get("fengpanTime"));
                                        }
                                        String kaijiangTimeAll = DateUtils.formatDate(startDate, DateUtils.datePattern) + kaijiangTime;
                                        String fengpanTimeAll = DateUtils.formatDate(startDate, DateUtils.datePattern) + fengpanTime;

                                        //查询上一期开奖时间
                                        lhcHandicapExample = new LhcHandicapExample();
                                        criteria = lhcHandicapExample.createCriteria();
                                        criteria.andStartlottoTimeLessThan(kaijiangTimeAll);
                                        lhcHandicapExample.setOrderByClause("issue desc");
                                        LhcHandicap lastLhcHandicap = lhcHandicapMapper.selectOneByExample(lhcHandicapExample);

                                        //查询这一年开奖的期数
                                        lhcHandicapExample = new LhcHandicapExample();
                                        criteria = lhcHandicapExample.createCriteria();
                                        //between的两个时间，比如2019年为： 2019-01-01 00:00:00  2020-01-01 00:00:00
                                        String betweenStartTime = kaijiangTimeAll.substring(0, 4) + "-01-01 00:00:00";
                                        String betweenEndTime = (Integer.valueOf(kaijiangTimeAll.substring(0, 4)) + 1) + "-01-01 00:00:00";
                                        criteria.andStartlottoTimeBetween(betweenStartTime, betweenEndTime);
                                        List<LhcHandicap> lhcHandicapList = lhcHandicapMapper.selectByExample(lhcHandicapExample);
                                        //这一年之前有的期数
                                        int size = lhcHandicapList.size();
                                        DecimalFormat df = new DecimalFormat("000");
                                        lhcHandicap1.setIssue(kaijiangTimeAll.substring(0, 4) + df.format(size + 1));
                                        lhcHandicap1.setStartlottoTime(kaijiangTimeAll);
                                        lhcHandicap1.setStartTime(lastLhcHandicap == null ? kaijiangTimeAll : lastLhcHandicap.getStartlottoTime());
                                        lhcHandicap1.setEndTime(fengpanTimeAll);
                                        lhcHandicap1.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
                                        lhcHandicap1.setAutomation(1);
                                        lhcHandicap1.setDeleted(0);

                                        lhcHandicapMapper.insert(lhcHandicap1);

                                        String jsonObject = JSON.toJSONString(lhcHandicap1);
                                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XGLHC_YUQI_DATA, "XglhcYuqidata@" + jsonObject);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("香港六合彩预开盘数据抓取出错", e);
        }
    }

}
