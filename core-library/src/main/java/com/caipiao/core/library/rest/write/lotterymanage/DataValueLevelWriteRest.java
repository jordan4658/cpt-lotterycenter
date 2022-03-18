package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.dto.lotterymanage.DataValueLevelDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface DataValueLevelWriteRest {


    /**
     * @param dataValueLevelDTO
     * @return
     */
    @PostMapping("/datavaluelevel/addOrUpdate.json")
    boolean addOrUpdate(@RequestBody DataValueLevelDTO dataValueLevelDTO);


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/datavaluelevel/deleteById.json")
    boolean deleteById(@RequestParam("id") Integer id);


   
    
    

	
}
