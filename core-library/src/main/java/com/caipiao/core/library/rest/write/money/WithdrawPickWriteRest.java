package com.caipiao.core.library.rest.write.money;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.constant.SystemInfoEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.SystemInfo;
import com.mapper.domain.WithdrawPick;

@FeignClient(name = BUSINESS_SERVER)
public interface WithdrawPickWriteRest {

    @PostMapping("/money/pickeditwithdrawPick.json")
    boolean pickeditwithdrawPick(@RequestParam("id") Integer id, @RequestParam("op") String op, @RequestParam("account") String account, @RequestParam("opid") Integer opid);
    
    
    @PostMapping("/money/opwithdrawPick.json")
    boolean opwithdrawPick(@RequestParam("id") Integer id, @RequestParam("optype") String optype, @RequestParam("mark") String mark, @RequestParam("opstatus") Integer opstatus);
    
    @PostMapping("/money/addupdateWithdrawPick.json")
    public boolean addupdateWithdrawPick(@RequestBody WithdrawPick withdrawPick);
    
    
    @PostMapping("/money/userWithdraw.json")
    ResultInfo<Map<String, Object>> userWithdraw(@RequestParam("money") double money, @RequestParam("id") Integer id, @RequestParam("bank") String bank, @RequestParam("source") String source);

    @PostMapping("/money/changeWithdrawed.json")
	String changeWithdrawed(@RequestParam("mid") String mid, @RequestParam("optype") String optype, @RequestParam("opvalue") String opvalue, @RequestParam("eddec") String eddec);
    
    @PostMapping("/money/getSystemInfo.json")
    public SystemInfo getSystemInfo(@RequestParam("key") String key);
}
