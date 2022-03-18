package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.DomainName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

/**
 * @Author: xiaomi
 * @CreateDate: 2019/1/7$ 14:20$
 * @Version: 1.0
 */
@FeignClient(name = BUSINESS_READ)
public interface DomainNameRest {

    /**
     * 查所有对应域名
     *
     * @param pageNum  页码
     * @param pageSize 条数
     * @param name     名称
     * @param type     类型
     * @return
     */
    @GetMapping("/domain/getDoMainList.json")
    PageResult<List<DomainName>> getDoMainList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name, @RequestParam("type") String type);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @GetMapping("/domain/getDomainById")
    DomainName getDomainById(@RequestParam("id") Integer id);

    /**
     * 获取所有数据
     *
     * @return
     */
    @GetMapping("/domain/getDomainALL.json")
    List<DomainName> getDomainALL();

    /**
     * 根据类型查找域名信息
     *
     * @param type 类型
     * @return
     */
    @PostMapping("/domain/findDomainByType.json")
    ResultInfo<DomainName> findDomainByType(@RequestParam("type") String type);
}
