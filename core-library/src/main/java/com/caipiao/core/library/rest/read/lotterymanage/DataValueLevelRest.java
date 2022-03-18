package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.lotterymanage.DataValueLevelVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.XSRDCOMMEND_READ;

@FeignClient(name = XSRDCOMMEND_READ)
public interface DataValueLevelRest {

    @GetMapping("/datavaluelevel/pagePage.json")
    PageResult<List<DataValueLevelVO>> queryPage(
            @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize
    );

    @GetMapping("/datavaluelevel/getAllDataValueLevelForLhcXs.json")
	List<DataValueLevelVO> getAllDataValueLevelForLhcXs(@RequestParam("code") String code);

    @GetMapping("/datavaluelevel/getDataValueLevelById.json")
    DataValueLevelVO getDataValueLevelById(@RequestParam("id") Integer id);

    
}
