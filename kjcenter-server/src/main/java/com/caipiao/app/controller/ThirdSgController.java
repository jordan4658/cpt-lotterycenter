package com.caipiao.app.controller;

import com.caipiao.app.service.LotterySgService;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.BaseUtil;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提供给第三方开奖中心
 * @author lzy
 * @create 2018-09-07 18:25
 **/
@RestController
@RequestMapping(value="/thirdsg",method = RequestMethod.GET)
public class ThirdSgController {
    private static final Logger logger = LoggerFactory.getLogger(ThirdSgController.class);

    @Autowired
    private LotterySgService lotterySgService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 开奖中心获取历史赛果信息，最近10期
     * 1:：更新token 直接从数据库更新， 2：想要更新ip 去掉缓存就好，
     * @return
     */
    @RequestMapping(value = "getSgInfos",method = RequestMethod.GET)
    public ResultInfo<Map<String, Object>> getSgInfos(@RequestParam(value = "token") String token,@RequestParam(value = "code") String code, HttpServletRequest request) {
        logger.info("getSgInfos：{},{}",token,code);
        ResultInfo<Map<String, Object>> resultInfo = ResultInfo.getInstance();
        try{
            //检查参数
            if (BaseUtil.checkToken(token, code)) {
                resultInfo.setStatus("0");
                resultInfo.setInfo("token或code错误");
                return resultInfo;
            }
            if(redisTemplate.opsForHash().get(RedisKeys.TOKEN_KEY,token) == null){
                resultInfo.setStatus("0");
                resultInfo.setInfo("token错误");
                return resultInfo;
            }

            String ips = String.valueOf(redisTemplate.opsForHash().get(RedisKeys.TOKEN_KEY,token));
            String requestIp = BaseUtil.getIpAddress(request);
            String sureIp = null;
            if(ips.equals("")){
                sureIp = requestIp;
            }else{
                List<String> ipList = java.util.Arrays.asList(ips.split("="));
                if(ips.split("=").length < 3){
                    if(!ipList.contains(requestIp)){
                        sureIp = ips+"="+requestIp;
                    }
                }else{
                    if(!ipList.contains(requestIp)){
                        resultInfo.setStatus("0");
                        resultInfo.setInfo("此token对应的ip已超过3个限制");
                        return resultInfo;
                    }
                }
            }
            if(sureIp != null){   //说明token  对应的ip 数目有变化
                redisTemplate.opsForHash().put(RedisKeys.TOKEN_KEY_RE,token,sureIp);
            }

            if(redisTemplate.opsForHash().get(RedisKeys.IP_VISIT_KEY,token+"="+requestIp+"="+code) == null){
                redisTemplate.opsForHash().put(RedisKeys.IP_VISIT_KEY,token+"="+requestIp+"="+code,
                        DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)+"="+"1");
            }else{
                String value = String.valueOf(redisTemplate.opsForHash().get(RedisKeys.IP_VISIT_KEY,token+"="+requestIp+"="+code));
                String array[] = value.split("=");
                String time = array[0];
                int cishu = Integer.valueOf(array[1]);
                double d= (new Date().getTime()-DateUtils.parseDate(time,DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime())/1000.0;
                System.out.println("描述："+d);
                if(d < 1.8){
                    cishu = cishu+1;
                    resultInfo.setStatus("0");
                    resultInfo.setInfo("违规"+cishu+"次/小时：按最新查询过快，请求间隔("+d+"秒)小于2秒");
                    redisTemplate.opsForHash().put(RedisKeys.IP_VISIT_KEY,token+"="+requestIp+"="+code,
                            DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)+"="+cishu);
                    return resultInfo;
                }

                redisTemplate.opsForHash().put(RedisKeys.IP_VISIT_KEY,token+"="+requestIp+"="+code,
                        DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)+"="+cishu);
            }

            resultInfo = lotterySgService.getLsSgInfoByIdLately(code,
                    1,10);
        }catch (Exception e){
            resultInfo.setStatus("0");
            resultInfo.setInfo("失败");
            logger.info("getLsSgInfoByIdAndDay出错：",e);
        }
        return resultInfo;
    }


}
