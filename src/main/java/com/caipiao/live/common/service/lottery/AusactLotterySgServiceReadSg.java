package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;

import java.util.List;
import java.util.Map;

public interface AusactLotterySgServiceReadSg {

    /**
     * 最近一期的开奖结果
     *
     * @return
     */
    Map<String, Object> getNewestSgInfo();

    /**
     * 历史开奖
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize);

    /**
     * @Title: singleAndDouble
     * @Description: 澳洲ACT大小 /单双 /五行
     * @author HANS
     * @date 2019年5月7日下午1:39:08
     */
    ResultInfo<List<Map<String, Object>>> algorithm(String type);

    /**
     * @return Map<String, Object>
     * @Title: getActSgLong
     * @Description: 获取澳洲ACT长龙
     * @author HANS
     * @date 2019年5月10日上午11:48:40
     */
    List<Map<String, Object>> getActSgLong();

    /**
     * @param caipiaoType
     * @return PlayAndOddListInfo
     * @Title: getAusactOddsList
     * @Description: 获取到赔率信息
     * @author HANS
     * @date 2019年5月11日下午10:34:19
     */
    PlayAndOddListInfoVO getAusactOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType, String cachKey);


    /**
     * @return
     * @Title: getLhcOddsList
     * @Description: 获取到六合彩的赔率信息
     * @author HANS
     * @date 2019年5月29日下午1:43:44
     */
    PlayAndOddListInfoVO getLhcOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType, String cachKey);

    /**
     * @return
     * @Title: getLhcZhengTeOddsList
     * @Description: 正特获取赔率
     * @author HANS
     * @date 2019年5月29日下午6:44:22
     */
    PlayAndOddListInfoVO getLhcZhengTeOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType, String cachKey, String zhengTeName);

}
