package com.caipiao.task.server.mapper;

import com.caipiao.task.server.mapper.sqlhelper.LotterySGRecordHelper;
import com.caipiao.task.server.pojo.LotterySGReocrd;
import com.caipiao.task.server.req.LotterySGRecordReq;
import com.mapper.domain.LotteryResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface LotteryResultBeanMapper {

    @Select("SELECT * FROM `lottery_result` s WHERE s.`lottery_id` = #{lotteryId} AND s.`cpk_number` != s.`kcw_number` AND s.`tb_status` != 'CONFIRMED' ORDER BY s.`issue` DESC LIMIT 15")
    List<LotteryResult> selectOutlierData(@Param("lotteryId") Integer lotteryId);


    /**
     * 查询开奖记录
     */
    @SelectProvider(type = LotterySGRecordHelper.class, method = "getSgRecordList")
    List<LotterySGReocrd> getSgRecordList(LotterySGRecordReq req);

}
