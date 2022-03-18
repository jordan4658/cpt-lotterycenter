package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.DoMainNameEnDTO;
import com.mapper.domain.DomainName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface DoMianNameWriteRest {


    /**
     * 添加
     * @param doMainNameEnDTO
     */
    @PostMapping("/domain/addDoamin.json")
    void addDomain(@RequestBody DoMainNameEnDTO doMainNameEnDTO);

    /**
     * 更新
     * @param nuberArr
     */
    @PostMapping("/domain/updateDoamin.json")
    void updateDoamin(@RequestBody List<DomainName> nuberArr);
}
