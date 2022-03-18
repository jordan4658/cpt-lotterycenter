package com.caipiao.core.library.rest.read.money;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.money.BalanceChangeVO;

import com.caipiao.core.library.vo.money.StatementVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface BalanceChangeRest {
	@RequestMapping("/blanceChange/pageBlanceChange.json")
	PageResult<List<BalanceChangeVO>> pageBlanceChange(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("type") Integer type, @RequestParam("account") String account);

	@RequestMapping("/blanceStatistics/blanceStatistics.json")
	Map<String, Object> blanceStatistics(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

	@RequestMapping("/blanceStatistics/statement.json")
	StatementVO statement(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);
}
