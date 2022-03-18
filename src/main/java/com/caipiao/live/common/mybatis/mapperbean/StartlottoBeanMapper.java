package com.caipiao.live.common.mybatis.mapperbean;


import com.caipiao.live.common.model.vo.OpenRepealOrderVO;
import com.caipiao.live.common.model.vo.RepealBetOrderVO;
import com.caipiao.live.common.model.vo.RepealStartlottoVO;
import com.caipiao.live.common.mybatis.mapperbean.provider.startlotto.StartlottoDynaSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface StartlottoBeanMapper {

    /**
     * 动态查询开放撤单列表
     * @param pageNo
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @return
     */
    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "listOpenRepealOrderVO")
    List<OpenRepealOrderVO> listOpenRepealOrderVO(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("lotteryId") Integer lotteryId, @Param("issue") String issue);

    /**
     * 动态查询开放撤单数量
     * @param lotteryId
     * @param issue
     * @return
     */
    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "countOpenRepealOrderVO")
    int countOpenRepealOrderVO(@Param("lotteryId") Integer lotteryId, @Param("issue") String issue);


    /**
     * 查询撤销注单列表
     * @param pageNo
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @param orderCode
     * @return
     */
    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "listRepealBetOrderVO")
    List<RepealBetOrderVO> listRepealBetOrderVO(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("lotteryId") Integer lotteryId, @Param("issue") String issue, @Param("orderCode") String orderCode);

    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "countRepealBetOrderVO")
    int countRepealBetOrderVO(@Param("lotteryId") Integer lotteryId, @Param("issue") String issue, @Param("orderCode") String orderCode);

    /**
     * 查询撤销开奖列表
     * @param pageNo
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @return
     */
    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "listRepealStartlottoVO")
    List<RepealStartlottoVO> listRepealStartlottoVO(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("lotteryId") Integer lotteryId, @Param("issue") String issue);

    @SelectProvider(type = StartlottoDynaSqlProvider.class, method = "countRepealStartlottoVO")
    int countRepealStartlottoVO(@Param("lotteryId") Integer lotteryId, @Param("issue") String issue);
}
