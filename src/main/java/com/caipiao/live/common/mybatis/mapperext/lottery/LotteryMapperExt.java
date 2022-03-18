package com.caipiao.live.common.mybatis.mapperext.lottery;

import com.caipiao.live.common.model.dto.LotKindDO;
import com.caipiao.live.common.model.vo.lottery.LotteryCateidVo;
import com.caipiao.live.common.model.vo.lottery.LotteryVO;
import com.caipiao.live.common.mybatis.entity.Lottery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Mapper
public interface LotteryMapperExt {

    List<Map<String, Object>> queryByLotteryFavorites(@Param("lotteryFavorites") List<Integer> lotteryIds);

    List queryLotteryCategoryAndLotteryInfo(@Param("type") String type);

    void deleteByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * 根据彩种开售状态同步更新彩票的开售状态
     *
     * @param workStatus
     * @param categoryId
     */
    void updateWorkStatusByCategoryId(@Param("workStatus") Integer workStatus, @Param("categoryId") Integer categoryId);

    Integer selectLotteryIsWork(@Param("lotteryId") Integer lotteryId);

    /**
     * 获取只属于直接间的彩种
     *
     * @return
     */
    Lottery getRandomForLive(List<Integer> list);

    /**
     * 获取只属于直接间的彩种V2
     * @return
     */
    Lottery getRandomForLiveV2();

    /**
     * 获取彩种类型返回到直播
     *
     * @return
     */
    List<LotteryVO> getLotKindList();

    List<LotteryCateidVo> getCateIdBlylLotteryId();
}
