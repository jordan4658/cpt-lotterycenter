package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.LhcGodType;
import com.mapper.domain.LhcVoteManage;

@FeignClient(name = BUSINESS_SERVER)
public interface LhcGodWriteRest {
	
	@RequestMapping("/lhcGod/getGodType.json")
	PageResult<List<LhcGodType>> getGodType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
	
	@RequestMapping("/lhcGod/getGodTypeAll.json")
	List<LhcGodType>getGodTypeAll();
	
	@RequestMapping("/lhcGod/addGodType.json")
	void addGodType(@RequestParam("name") String name, @RequestParam("photoUrl") String photoUrl);
	
	@RequestMapping("/lhcGod/updateGodType.json")
	void updateGodType(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("photoUrl") String photoUrl);
	
	@RequestMapping("/lhcGod/deleteGodType.json")
	boolean deleteGodType(@RequestParam("id") String id);
	
	@RequestMapping("/lhcGod/deleteOrUpdateOrAddVoteMange.json")
	boolean deleteOrUpdateOrAddVoteMange(@RequestParam("list") String list);
	
	@RequestMapping("/lhcGod/deleteVoteMange.json")
	boolean deleteVoteMange();
	
	@RequestMapping("/lhcGod/getVoteManage.json")
	List<LhcVoteManage> getVoteManage();
	
	@RequestMapping("/lhcGod/addVotesInfo.json")
	Map<String,String> addVotesInfo(@RequestParam("uid") String uid, @RequestParam("id") int id);
	
	}
