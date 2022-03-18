package com.caipiao.live.common.mybatis.mapperbean;

import com.caipiao.live.common.model.vo.ManualStartlottoRecordVO;
import com.caipiao.live.common.mybatis.mapperbean.provider.startlotto.ManualRecordDynaSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface ManualRecordBeanMapper {

    /**
     * 根据条件查询手动开奖记录
     * @param pageNo
     * @param pageSize
     * @param lotteryId
     * @param issue
     * @return
     */
    @SelectProvider(type = ManualRecordDynaSqlProvider.class, method = "listManualStartlottoRecord")
    List<ManualStartlottoRecordVO> listManualStartlottoRecord(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("lotteryId") Integer lotteryId, @Param("issue") String issue);

    /**
     * 根据条件查询手动开奖记录数量
     * @param lotteryId
     * @param issue
     * @return
     */
    @SelectProvider(type = ManualRecordDynaSqlProvider.class, method = "countManualStartlottoRecord")
    int countManualStartlottoRecord(@Param("lotteryId") Integer lotteryId, @Param("issue") String issue);

}
