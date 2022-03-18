package com.caipiao.core.library.rest.write.interfacelog;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface ProblemComplaintServiceWriteRest extends BaseRest {

    /**
     * 改变菜单排序
     * @param id
     * @param moveId
     * @return
     */
    @PostMapping("pcrWrite/moveMenuInfo.json")
    ResultInfo<Void> moveMenuInfo(@RequestParam("id") int id, @RequestParam("moveId") String moveId);

    /**
     * 切换菜单状态
     * @param menuId 菜单id
     * @return
     */
    @PostMapping("pcrWrite/cutMenuStatus.json")
    ResultInfo<Void> cutMenuStatus(@RequestParam("menuId") int menuId);

    /**
     * 新增菜单
     * @return
     */
    @PostMapping("pcrWrite/insertMenuInfo.json")
    ResultInfo<Void> insertMenuInfo(@RequestParam("name") String name,
                                    @RequestParam("parentId") int parentId);

    /**
     * 修改菜单
     * @return
     */
    @PostMapping("pcrWrite/updateMenuInfo.json")
    ResultInfo<Void> updateMenuInfo(@RequestParam("id") int id, @RequestParam("name") String name,
                                    @RequestParam("parentId") int parentId);

    /**
     * 删除菜单及其绑定的资源
     * @param menuId
     * @return
     */
    @PostMapping("pcrWrite/delMenu.json")
    ResultInfo<Void> delMenu(@RequestParam("menuId") int menuId);

}
