package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.Lottery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LotteryWriteRest extends BaseRest {

	@PostMapping("/lottery/insert.json")
	boolean insert(@RequestBody Lottery lottery);

	@PostMapping("/lottery/update.json")
	boolean update(@RequestBody Lottery lottery);

	@PostMapping("/lottery/delete.json")
	boolean delete(@RequestParam(value = "id") Integer id);

	/**
	 * 编辑彩种信息
	 * @param lottery 彩种信息
	 */
	@PostMapping("/lottery/doEditLottery.json")
    Boolean doEditLottery(@RequestBody Lottery lottery);

	@PostMapping("/lottery/addXyftData.json")
    public void addXyftData(@RequestParam("date") String date);
	
	
	@PostMapping("/lottery/addXjsscData.json")
    public void addXjsscData(@RequestParam("date") String date);
	
	
	@PostMapping("/lottery/addTxffcData.json")
    public void addTxffcData(@RequestParam("date") String date);
	
	
	@PostMapping("/lottery/addPjpk10Data.json")
    public void addPjpk10Data(@RequestParam("date") String date);
	
	
	@PostMapping("/lottery/addPceggData.json")
    public void addPceggData(@RequestParam("date") String date);
	
	
	@PostMapping("/lottery/addCqsscData.json")
    public void addCqsscData(@RequestParam("date") String date);

}
