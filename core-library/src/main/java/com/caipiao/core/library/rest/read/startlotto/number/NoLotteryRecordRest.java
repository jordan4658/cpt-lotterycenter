package com.caipiao.core.library.rest.read.startlotto.number;

import com.caipiao.core.library.dto.startlotto.NumberRecordDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.startlotto.number.NoLotteryRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface NoLotteryRecordRest {

    /**
     * 开奖号码跳开记录列表
     * @param numberRecordDTO
     * @return
     */
    @PostMapping("/pageNoLotteryRecord.json")
    PageResult<List<NoLotteryRecordVO>> pageNoLotteryRecord(@RequestBody NumberRecordDTO numberRecordDTO);
}
