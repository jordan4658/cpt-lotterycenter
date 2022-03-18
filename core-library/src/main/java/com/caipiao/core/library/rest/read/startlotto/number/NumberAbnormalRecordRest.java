package com.caipiao.core.library.rest.read.startlotto.number;

import com.caipiao.core.library.dto.startlotto.NumberRecordDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.startlotto.number.NumberAbnormalRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface NumberAbnormalRecordRest {

    /**
     * 开奖号码异常记录列表
     * @param numberRecordDTO
     * @return
     */
    @PostMapping("/pageNumberAbnormalRecord.json")
    PageResult<List<NumberAbnormalRecordVO>> pageNumberAbnormalRecord(@RequestBody NumberRecordDTO numberRecordDTO);
}
